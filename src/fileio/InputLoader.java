package fileio;

import com.fasterxml.jackson.core.JsonParser;
import constants.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputLoader {

    private final String inputFile;

    public InputLoader(final String inputFile) {
        this.inputFile = inputFile;
    }

    public String getInputFile() {
        return inputFile;
    }

    public Input readData() {
        JSONParser jsonParser = new JSONParser();
        List<Consumer> consumers = new ArrayList<>();
        List<Distributor> distributors = new ArrayList<>();
        List<MonthlyUpdate> monthlyUpdates = new ArrayList<>();
        CommonFactory commonFactory = new CommonFactory();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(this.inputFile));
            JSONObject initialData = (JSONObject) jsonObject.get(Constants.INITIALDATA);
            JSONArray jsonConsumers = (JSONArray) initialData.get(Constants.CONSUMERS);
            JSONArray jsonDistributors = (JSONArray) initialData.get(Constants.DISTRIBUTORS);
            JSONArray jsonMonthlyUpdates = (JSONArray) jsonObject.get(Constants.MONTHLYUPDATES);

            if (jsonMonthlyUpdates != null) {
                for (Object jsonUpdate : jsonMonthlyUpdates) {
                    List<Consumer> newConsumers = new ArrayList<>();
                    List<CostChange> costChanges = new ArrayList<>();
                    JSONArray jsonNewConsumers =
                            (JSONArray) ((JSONObject) jsonUpdate).get(Constants.NEWCONSUMERS);
                    JSONArray jsonCostChanges =
                            (JSONArray) ((JSONObject) jsonUpdate).get(Constants.COSTSCHANGES);

                    for (Object jsonIteratorNewConsumers : jsonNewConsumers) {
                        newConsumers.add(new Consumer(
                                Integer.parseInt(
                                        ((JSONObject) jsonIteratorNewConsumers).get(Constants.ID)
                                                .toString()),
                                Integer.parseInt(((JSONObject) jsonIteratorNewConsumers)
                                        .get(Constants.INITIALBUDGET).toString()),
                                Integer.parseInt(((JSONObject) jsonIteratorNewConsumers)
                                        .get(Constants.MONTHLYINCOME).toString())
                        ));
                    }

                    for (Object jsonIteratorCostChanges : jsonCostChanges) {
                        costChanges.add(new CostChange(
                                Integer.parseInt(
                                        ((JSONObject) jsonIteratorCostChanges).get(Constants.ID)
                                                .toString()),
                                Integer.parseInt(((JSONObject) jsonIteratorCostChanges)
                                        .get(Constants.INFRASTRUCTURECOST).toString()),
                                Integer.parseInt(((JSONObject) jsonIteratorCostChanges)
                                        .get(Constants.PRODUCTIONCOST).toString())
                        ));
                    }

                    monthlyUpdates.add(new MonthlyUpdate(newConsumers, costChanges));
                }
            }

            if (jsonConsumers != null) {
                for (Object jsonConsumer : jsonConsumers) {
                    consumers.add((Consumer) commonFactory.getCommon("consumer",
                            Integer.parseInt(
                                    ((JSONObject) jsonConsumer).get(Constants.ID).toString()),
                            Integer.parseInt(
                                    ((JSONObject) jsonConsumer).get(Constants.INITIALBUDGET)
                                            .toString()),
                            Integer.parseInt(
                                    ((JSONObject) jsonConsumer).get(Constants.MONTHLYINCOME)
                                            .toString()),
                            0, 0, 0)
                    );
                }
            }

            if (jsonDistributors != null) {
                for (Object jsonDistributor : jsonDistributors) {
                    distributors.add((Distributor) commonFactory.getCommon("distributor",
                            Integer.parseInt(
                                    ((JSONObject) jsonDistributor).get(Constants.ID).toString()),
                            Integer.parseInt(
                                    ((JSONObject) jsonDistributor).get(Constants.INITIALBUDGET)
                                            .toString()),
                            0,
                            Integer.parseInt(
                                    ((JSONObject) jsonDistributor).get(Constants.CONTRACTLENGTH)
                                            .toString()),
                            Integer.parseInt(((JSONObject) jsonDistributor)
                                    .get(Constants.INITIALINFRACTURECOST).toString()),
                            Integer.parseInt(((JSONObject) jsonDistributor)
                                    .get(Constants.INITIALPRODUCTIONCOST).toString())
                    ));
                }
            }

            if (jsonConsumers == null) {
                consumers = null;
            }

            if (jsonDistributors == null) {
                distributors = null;
            }

            if (jsonMonthlyUpdates == null) {
                monthlyUpdates = null;
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return new Input(consumers, distributors, monthlyUpdates);
    }
}
