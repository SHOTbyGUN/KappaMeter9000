/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.util.Enumeration;
import java.util.Set;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import static kappameter9000.KappaMeter9000.shutdown;

/**
 *
 * @author s12100
 */
public class SettingsGUI {
    
    // ROOT
    private TabPane rootTabPane;
    
    // Channels
    private ListView channelsListView;
    private Button channelsAddButton, channelsRemoveButton;
    private TextField channelsAddTextField;
    
    // COMMON
    private Tab channelsTab, ircTab, kappasTab, sysLogTab, msgLogTab;
    private GridPane channelsGridPane, ircGridPane, kappasGridPane;
    
    // IRC
    private TextField ircServer, ircPort, ircUserName;
    private PasswordField ircPassword;
    private Button ircConnect, ircDisconnect;
    
    // MSGLOG
    private TextArea msgLogArea;
    
    // SYSLOG
    private TextArea sysLogArea;
    
    int gridRow = 0;

    public SettingsGUI() {
        
        rootTabPane = new TabPane();
        
        // Channels SETTINGS INIT
        channelsTab = new Tab("Channels");
        channelsTab.setClosable(false);
        channelsGridPane = createGridPane();
        channelsListView = new ListView<>();
        channelsAddButton = new Button("Add");
        channelsAddButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    Channel channel = new Channel(channelsAddTextField.getText());
                    Static.channels.put(channel.getName(), channel);
                    updateChannelsListView();
                } catch (Exception ex) {
                    Static.log("error adding channel " + ex.toString());
                }
            }
        });
        channelsRemoveButton = new Button("Remove");
        channelsRemoveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    Static.channels.get(((String) channelsListView.getSelectionModel().getSelectedItem())).removeThisChannel();
                    updateChannelsListView();
                } catch (Exception ex) {
                    Static.log("error removing channel " + ex.toString());
                }
            }
        });
        channelsAddTextField = new TextField();
        
        // NETWORK SETTINGS
        kappasTab = new Tab("Kappas");
        kappasTab.setClosable(false);
        kappasGridPane = createGridPane();
        
        // IRC SETTINGS INIT
        ircGridPane = createGridPane();
        ircTab = new Tab("IRC Settings");
        ircTab.setClosable(false);
        ircServer = new TextField(Static.settings.get("ircServer"));
        ircPort = new TextField(Static.settings.get("ircPort"));
        ircUserName = new TextField(Static.settings.get("ircUserName"));
        ircPassword = new PasswordField();
        ircPassword.setText(Static.settings.get("ircPassword"));
        ircConnect = new Button("Connect");
        ircConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    if(Static.ircbot != null)
                        Static.ircbot.disconnect();
                    
                    Static.ircbot = new IrcClient(ircUserName.getText());
                    Static.ircbot.connect(ircServer.getText(), Integer.parseInt(ircPort.getText()), ircPassword.getText());
                    if(Static.ircbot.isConnected()) {
                        Static.log("Connected");
                    } else {
                        Static.log("Not connected");
                    }
                } catch (Exception ex) {
                    Static.log("error connecting to irc! " + ex.toString());
                }
            }
        });
        ircDisconnect = new Button("Disconnect");
        ircDisconnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    if(Static.ircbot == null)
                        Static.log("there is no ircbot object");
                    else
                        Static.ircbot.disconnect();
                } catch (Exception ex) {
                    Static.log("error disconnecting from irc! " + ex.toString());
                }
            }
        });
        
        
        // SYSLOG SETTINGS INIT
        sysLogTab = new Tab("System Log");
        sysLogTab.setClosable(false);
        sysLogArea = new TextArea("Starting application");
        
        // MSGLOG SETTINGS INIT
        msgLogTab = new Tab("Message Log");
        msgLogTab.setClosable(false);
        msgLogArea = new TextArea();
    }
    
    public void createStage() {
        
        // DEFINE THE GUI
        
        // IRC TAB DEFINE
        rootTabPane.getTabs().add(ircTab);
        ircTab.setContent(ircGridPane);
        gridRow = 0;
        
        // Server
        ircGridPane.add(new Label("Server:"), 0, gridRow);
        ircGridPane.add(ircServer, 1, gridRow++);
        // Port
        ircGridPane.add(new Label("Port:"), 0, gridRow);
        ircGridPane.add(ircPort, 1, gridRow++);
        // Username
        ircGridPane.add(new Label("Username:"), 0, gridRow);
        ircGridPane.add(ircUserName, 1, gridRow++);
        // Password
        ircGridPane.add(new Label("Password:"), 0, gridRow);
        ircGridPane.add(ircPassword, 1, gridRow++);
        // Password hint
        ircGridPane.add(new Label("Get new OAUTH from:"), 0, gridRow);
        ircGridPane.add(new Label("http://twitchapps.com/tmi/"), 1, gridRow++);
        
        // Connect or Disconnect
        ircGridPane.add(ircConnect, 0, gridRow);
        ircGridPane.add(ircDisconnect, 1, gridRow++);
        
        // CHANNELS TAB
        rootTabPane.getTabs().add(channelsTab);
        channelsTab.setContent(channelsGridPane);
        gridRow = 0;
        
        // CHANNELS Add
        channelsGridPane.add(new Label("Channel Name"), 0, gridRow);
        channelsGridPane.add(channelsAddTextField, 1, gridRow++);
        
        channelsGridPane.add(new Label("Add channel"), 0, gridRow);
        channelsGridPane.add(channelsAddButton, 1, gridRow++);
        
        channelsGridPane.add(new Label("Channels"), 0, gridRow);
        channelsGridPane.add(channelsListView, 1, gridRow++);
        updateChannelsListView();
        
        channelsGridPane.add(new Label("Remove selected channel"), 0, gridRow);
        channelsGridPane.add(channelsRemoveButton, 1, gridRow++);
        
        // KAPPAS TAB DEFINE
        rootTabPane.getTabs().add(kappasTab);
        kappasTab.setContent(kappasGridPane);
        
        // SYSLOG TAB DEFINE
        rootTabPane.getTabs().add(sysLogTab);
        sysLogTab.setContent(sysLogArea);
        
        // MSGLOG TAB DEFINE
        rootTabPane.getTabs().add(msgLogTab);
        msgLogTab.setContent(msgLogArea);
        
        gridRow = 0;
        
        Scene scene = new Scene(rootTabPane, 400, 600);
        
        Stage stage = new Stage();
        stage.setTitle("KappaMeter9000 Settings");
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                shutdown();
            }
        });
    }
    
    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        return grid;
    }
    
    public void writeSysLog(String logMessage) {
        if(Platform.isFxApplicationThread()) {
            sysLogArea.appendText(logMessage);
        } else {
            System.out.println("writeLog not in jfx thread!!!!!");
        }
    }
    
    public void writeMsgLog(String logMessage) {
        if(Platform.isFxApplicationThread()) {
            msgLogArea.appendText(logMessage);
        } else {
            System.out.println("writeLog not in jfx thread!!!!!");
        }
    }
    
    public void updateChannelsListView() {
        channelsListView.getItems().setAll(Static.channels.keySet());
    }
    
    
}
