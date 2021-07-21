package View;

import Controller.DeckProgramController;
import Controller.LoginProgramController;
import Model.Cards;
import Model.Deck;
import Model.Response;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckController extends Application {
    public static DeckController deckController;
    private static Stage stage;
    public static File mediaFile = new File("src/main/resources/audio files/button hit sound effect.mp3");
    public static Media media;
    public TextField deckName = new TextField();
    public Label info=new Label();
    static {
        try {
            media = new Media(mediaFile.toURI().toURL().toString());
        } catch (MalformedURLException ignored) {
        }
    }

    public static MediaPlayer mediaPlayer = new MediaPlayer(media);

    public static DeckController getInstance() {
        if (deckController == null) deckController = new DeckController();
        return deckController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        DeckController.stage = stage;
        stage.setTitle("Yu-Gi-Oh");
        URL welcomeUrl = getClass().getResource("/fxml/DeckMenu.fxml");
        Parent root = FXMLLoader.load(welcomeUrl);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/welcomeStyle.css").toExternalForm());
        stage.setScene(scene);
    }

    public void createDeck() {
        if (DeckProgramController.getInstance().checkDeckNameExist(deckName.getText())) {
            Button btn = new Button();
            btn.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.getChildren().add(new Text("deck with name " + deckName.getText() + " already exists"));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    });
        } else {
            new Deck(deckName.getText(), LoginProgramController.loginUsername);
            Button btn = new Button();
            btn.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.getChildren().add(new Text(Response.deckCreateSuccessfully));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    });
        }
    }

    public void deckShowAll() {
        System.out.println("Decks:");
        System.out.println("Active deck:");
        ArrayList<String> activeDeck = new ArrayList<>();
        ArrayList<String> otherDecks = new ArrayList<>();
        for (Deck deck : Deck.decks) {
            if (deck.getOwner().equals(LoginProgramController.loginUsername)) {
                if (deck.isActive()) {
                    activeDeck.add(deck.getDeckName());
                } else {
                    otherDecks.add(deck.getDeckName());
                }
            }
        }
        Collections.sort(activeDeck);
        Collections.sort(otherDecks);
        String validOrNot;
        for (String active : activeDeck) {
            if (Deck.getDeckByName(active).isInvalidDeck())
                validOrNot = "invalid";
            else
                validOrNot = "valid";
            Button btn = new Button();
            String finalValidOrNot = validOrNot;
            btn.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.getChildren().add(new Text(active + ":" + " main deck " + Deck.getDeckByName(active).getNumberOfCardsInDecks() + "," + " side deck " + Deck.getDeckByName(active).getNuberOfCardsInSideDecks() + ", " + finalValidOrNot));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    });
        }
        System.out.println("Other decks:");
        for (String other : otherDecks) {
            if (Deck.getDeckByName(other).isInvalidDeck())
                validOrNot = "invalid";
            else
                validOrNot = "valid";
            Button btn = new Button();
            String finalValidOrNot1 = validOrNot;
            btn.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.getChildren().add(new Text(other + ":" + " main deck " + Deck.getDeckByName(other).getNumberOfCardsInDecks() + "," + " side deck " + Deck.getDeckByName(other).getNuberOfCardsInSideDecks() + ", " + finalValidOrNot1));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    });
        }
    }

    public void showDeck() {
        String deck = DeckProgramController.getInstance().deckShow(Pattern.compile("").matcher("deck show --deck-name " + deckName.getText())).get(0);
        //String side = DeckProgramController.getInstance().deckShow(deckName.getText()).get(1);
        if (Deck.getDeckByName(deck) == null) {
            System.out.println("deck with name " + deck + " does not exist");
        } else {
            Button btn = new Button();
            btn.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.getChildren().add(new Text("Deck: " + deck + "\n"+"Main deck:"+"\n"+"Monsters:"));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    });

                for (String cards : Deck.getDeckByName(deck).getCardsInDecks()) {
                    if (Cards.getCardByName(cards).getStyle().equals("MONSTER"))
                        info.setText(cards + ":" + Cards.getCardByName(cards).getDescription());
                    }

            btn.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.getChildren().add(new Text("Spell and Traps:"));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    });
                for (String cards : Deck.getDeckByName(deck).getCardsInDecks()) {
                    if (Cards.getCardByName(cards).getStyle().equals("SPELL") || Cards.getCardByName(cards).getStyle().equals("TRAP"))
                        info.setText(cards + ":" + Cards.getCardByName(cards).getDescription());
                }
//                else {
//                    System.out.println("Side deck:");
//                    System.out.println("Monsters:");
//                    for (String cards : Deck.getDeckByName(deck).getCardsInSideDecks()) {
//                        if (Cards.getCardByName(cards).getStyle().equals("MONSTER"))
//                            System.out.println(cards + ":" + Cards.getCardByName(cards).getDescription());
//                    }
            btn.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.getChildren().add(new Text("Spells and Traps: "));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    });
            for (String cards : Deck.getDeckByName(deck).getCardsInSideDecks()) {
                if (Cards.getCardByName(cards).getStyle().equals("SPELL") || Cards.getCardByName(cards).getStyle().equals("TRAP"))
                    info.setText(cards + ":" + Cards.getCardByName(cards).getDescription());
            }
        }
    }

    public void deleteDeck() {
            if (DeckProgramController.getInstance().checkDeckNameExist(deckName.getText())) {
                Button btn = new Button();
                btn.setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                final Stage dialog = new Stage();
                                dialog.initModality(Modality.APPLICATION_MODAL);
                                dialog.initOwner(stage);
                                VBox dialogVbox = new VBox(20);
                                dialogVbox.getChildren().add(new Text("deck with name " + deckName.getText() + " already exists"));
                                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                                dialog.setScene(dialogScene);
                                dialog.show();
                            }
                        });
            } else {
                Deck.decks.remove(Deck.getDeckByName(deckName.getText()));
                Button btn = new Button();
                btn.setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                final Stage dialog = new Stage();
                                dialog.initModality(Modality.APPLICATION_MODAL);
                                dialog.initOwner(stage);
                                VBox dialogVbox = new VBox(20);
                                dialogVbox.getChildren().add(new Text(Response.deckDeleteSuccessfully));
                                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                                dialog.setScene(dialogScene);
                                dialog.show();
                            }
                        });
            }
    }

    public void setActiveDeck() {
            if (!DeckProgramController.getInstance().checkDeckNameExist(deckName.getText())) {
                Button btn = new Button();
                btn.setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                final Stage dialog = new Stage();
                                dialog.initModality(Modality.APPLICATION_MODAL);
                                dialog.initOwner(stage);
                                VBox dialogVbox = new VBox(20);
                                dialogVbox.getChildren().add(new Text("deck with name " + deckName.getText() + " does not exist"));
                                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                                dialog.setScene(dialogScene);
                                dialog.show();
                            }
                        });
            } else {
                Deck.getDeckByName(deckName.getText()).setActive(true);
                Button btn = new Button();
                btn.setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                final Stage dialog = new Stage();
                                dialog.initModality(Modality.APPLICATION_MODAL);
                                dialog.initOwner(stage);
                                VBox dialogVbox = new VBox(20);
                                dialogVbox.getChildren().add(new Text("deck activated successfully"));
                                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                                dialog.setScene(dialogScene);
                                dialog.show();
                            }
                        });
            }
    }

    public void deckRemoveCard() {
            String card = DeckProgramController.getInstance().deckRemoveCard(Pattern.compile("").matcher("deck show --deck-name " + deckName.getText())).get(0);
            String deck = DeckProgramController.getInstance().deckRemoveCard(Pattern.compile("").matcher("deck show --deck-name " + deckName.getText())).get(1);
            String side = DeckProgramController.getInstance().deckRemoveCard(Pattern.compile("").matcher("deck show --deck-name " + deckName.getText())).get(2);
            if (Deck.getDeckByName(deck) == null) {
                System.out.println("deck with name " + deck + " does not exist");
            } else if (!Deck.getDeckByName(deck).getCardsInDecks().contains(card) && side == null) {
                System.out.println("card with name" + card + " does not exist in main deck");
            } else if (!Deck.getDeckByName(deck).getCardsInSideDecks().contains(card) && side != null) {
                System.out.println("card with name" + card + " does not exist in side deck");
            } else {
                if (side == null) {
                    Deck.getDeckByName(deck).removeCardsInDecks(card);
                } else {
                    Deck.getDeckByName(deck).removeCardsInSideDecks(card);
                }
            }
    }

    public void deckAddCard() {
            String card = DeckProgramController.getInstance().deckAddCard(Pattern.compile("").matcher("deck show --deck-name " + deckName.getText())).get(0);
            String deck = DeckProgramController.getInstance().deckAddCard(Pattern.compile("").matcher("deck show --deck-name " + deckName.getText())).get(1);
            String side = DeckProgramController.getInstance().deckAddCard(Pattern.compile("").matcher("deck show --deck-name " + deckName.getText())).get(2);
            if (!LoginProgramController.loginUsername.getPlayerCards().contains(card)) {
                Button btn = new Button();
                btn.setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                final Stage dialog = new Stage();
                                dialog.initModality(Modality.APPLICATION_MODAL);
                                dialog.initOwner(stage);
                                VBox dialogVbox = new VBox(20);
                                dialogVbox.getChildren().add(new Text("card with name " + card + " does not exist"));
                                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                                dialog.setScene(dialogScene);
                                dialog.show();
                            }
                        });
            } else if (Deck.getDeckByName(deck) == null) {
                Button btn = new Button();
                btn.setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                final Stage dialog = new Stage();
                                dialog.initModality(Modality.APPLICATION_MODAL);
                                dialog.initOwner(stage);
                                VBox dialogVbox = new VBox(20);
                                dialogVbox.getChildren().add(new Text("deck with name " + deck + " does not exist"));
                                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                                dialog.setScene(dialogScene);
                                dialog.show();
                            }
                        });
            } else if (Deck.getDeckByName(deck).getAllCardsNumber() == 60 && side == null) {
                Button btn = new Button();
                btn.setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                final Stage dialog = new Stage();
                                dialog.initModality(Modality.APPLICATION_MODAL);
                                dialog.initOwner(stage);
                                VBox dialogVbox = new VBox(20);
                                dialogVbox.getChildren().add(new Text("main deck is full"));
                                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                                dialog.setScene(dialogScene);
                                dialog.show();
                            }
                        });
            } else if (Deck.getDeckByName(deck).getAllCardsNumber() == 15 && side != null) {
                Button btn = new Button();
                btn.setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                final Stage dialog = new Stage();
                                dialog.initModality(Modality.APPLICATION_MODAL);
                                dialog.initOwner(stage);
                                VBox dialogVbox = new VBox(20);
                                dialogVbox.getChildren().add(new Text("side deck is full"));
                                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                                dialog.setScene(dialogScene);
                                dialog.show();
                            }
                        });
            } else if (DeckProgramController.getInstance().isDeckFull(deck, card)) {
                Button btn = new Button();
                btn.setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                final Stage dialog = new Stage();
                                dialog.initModality(Modality.APPLICATION_MODAL);
                                dialog.initOwner(stage);
                                VBox dialogVbox = new VBox(20);
                                dialogVbox.getChildren().add(new Text("there are already three cards with name " + card + " in deck " + deck));
                                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                                dialog.setScene(dialogScene);
                                dialog.show();
                            }
                        });
            } else {
                if (side == null) {
                    Deck.getDeckByName(deck).setCardsInDecks(card);
                } else {
                    Deck.getDeckByName(deck).setCardsInSideDecks(card);
                }
            }

    }

    public void showDeckCard() {
            Collections.sort(Deck.getDeckByName(LoginProgramController.loginUsername.getUsername()).getCardsInDecks());
            for (int i = 0; i < Deck.getDeckByName(LoginProgramController.loginUsername.getUsername()).getCardsInDecks().size(); ++i) {
                info.setText(Deck.getDeckByName(LoginProgramController.loginUsername.getUsername()).getCardsInDecks().get(i) + Cards.getCardByName
                        (Deck.getDeckByName(LoginProgramController.loginUsername.getUsername()).getCardsInDecks().get(i)).getDescription());
            }

    }

    public void back(MouseEvent mouseEvent) throws Exception {
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
            }
        });
        MenuController.getInstance().start(stage);
    }
}
