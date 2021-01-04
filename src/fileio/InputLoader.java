package fileio;

import constants.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class InputLoader {

    private final String inputFile;

    public InputLoader(final String inputFile) {
        this.inputFile = inputFile;
    }

    public String getInputFile() {
        return inputFile;
    }
    /**
     * Read the data from the JSON file and parse it into object input
     * @return the input of class Input
     */
    public Input readData() {
        JSONParser jsonParser = new JSONParser();
        List<Consumer> consumers = new ArrayList<>();
        List<Distributor> distributors = new ArrayList<>();
        List<Producer> producers = new ArrayList<>();
        List<MonthlyUpdate> monthlyUpdates = new ArrayList<>();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(this.inputFile));
            JSONObject initialData = (JSONObject) jsonObject.get(Constants.INITIALDATA);
            JSONArray jsonConsumers = (JSONArray) initialData.get(Constants.CONSUMERS);
            JSONArray jsonDistributors = (JSONArray) initialData.get(Constants.DISTRIBUTORS);
            JSONArray jsonProducers = (JSONArray) initialData.get(Constants.PRODUCERS);
            JSONArray jsonMonthlyUpdates = (JSONArray) jsonObject.get(Constants.MONTHLYUPDATES);

            if (jsonMonthlyUpdates != null) {
                for (Object jsonUpdate : jsonMonthlyUpdates) {
                    List<Consumer> newConsumers = new ArrayList<>();
                    List<DistributorChange> distributorChanges = new ArrayList<>();
                    List<ProducerChange> producerChanges = new ArrayList<>();
                    JSONArray jsonNewConsumers =
                            (JSONArray) ((JSONObject) jsonUpdate).get(Constants.NEWCONSUMERS);
                    JSONArray jsonDistributorChanges =
                            (JSONArray) ((JSONObject) jsonUpdate).get(Constants.DISTRIBUTORCHANGES);
                    JSONArray jsonProducerChanges =
                            (JSONArray) ((JSONObject) jsonUpdate).get(Constants.PRODUCERCHANGES);

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

                    for (Object jsonIteratorDistributorChanges : jsonDistributorChanges) {
                        distributorChanges.add(new DistributorChange(
                                Integer.parseInt(
                                        ((JSONObject) jsonIteratorDistributorChanges).get(Constants.ID)
                                                .toString()),
                                Integer.parseInt(((JSONObject) jsonIteratorDistributorChanges)
                                        .get(Constants.INFRASTRUCTURECOST).toString())
                        ));
                    }

                    for (Object jsonIteratorProducerChanges : jsonProducerChanges) {
                        producerChanges.add(new ProducerChange(
                                Integer.parseInt(
                                        ((JSONObject) jsonIteratorProducerChanges).get(Constants.ID)
                                        .toString()),
                                Integer.parseInt(
                                        ((JSONObject) jsonIteratorProducerChanges)
                                                .get(Constants.ENERGYPERDISTRIBUTOR).toString())
                        ));
                    }

                    monthlyUpdates.add(new MonthlyUpdate(newConsumers, distributorChanges,
                            producerChanges));
                }
            }

            if (jsonConsumers != null) {
                for (Object jsonConsumer : jsonConsumers) {
                    consumers.add((Consumer) CommonFactory.getInstance().getCommon(
                            "consumer",
                            Integer.parseInt(
                                    ((JSONObject) jsonConsumer).get(Constants.ID).toString()),
                            Integer.parseInt(
                                    ((JSONObject) jsonConsumer).get(Constants.INITIALBUDGET)
                                            .toString()),
                            Integer.parseInt(
                                    ((JSONObject) jsonConsumer).get(Constants.MONTHLYINCOME)
                                            .toString()),
                            0, 0, 0, null,
                            null, 0, 0.0, 0)
                    );
                }
            }

            if (jsonDistributors != null) {
                for (Object jsonDistributor : jsonDistributors) {
                    distributors.add((Distributor) CommonFactory.getInstance().getCommon(
                            "distributor",
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
                                    .get(Constants.ENERGYNEEDEDKW).toString()),
                            (String )((JSONObject) jsonDistributor).get(Constants.PRODUCERSTRATEGY),
                            null, 0 , 0.0,
                            0
                    ));
                }
            }

            if(jsonProducers != null) {
                for (Object jsonProducer : jsonProducers) {
                    producers.add((Producer) CommonFactory.getInstance().getCommon(
                            "producer",
                            Integer.parseInt(
                                    ((JSONObject) jsonProducer).get(Constants.ID).toString()),
                            0, 0, 0, 0,
                            0, null,
                            (String) ((JSONObject) jsonProducer).get(Constants.ENERGYTYPE),
                            Integer.parseInt(
                                    ((JSONObject) jsonProducer).get(Constants.MAXDISTRIBUTORS)
                                            .toString()),
                            Double.parseDouble(
                                    ((JSONObject) jsonProducer).get(Constants.PRICEKW).toString()),
                            Integer.parseInt(
                                    ((JSONObject) jsonProducer).get(Constants.ENERGYPERDISTRIBUTOR)
                                    .toString())
                    ));
                }
            }

            if(jsonProducers == null) {
                producers = null;
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

        return new Input(consumers, distributors, monthlyUpdates, producers);
    }
}
