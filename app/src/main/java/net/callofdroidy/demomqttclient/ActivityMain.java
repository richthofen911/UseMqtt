package net.callofdroidy.demomqttclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.MqttService;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import java.util.Enumeration;

public class ActivityMain extends AppCompatActivity implements MqttCallback{

    private MqttAndroidClient mqttAndroidClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), "tcp://m2m.eclipse.org", "Matata");

        try{
            mqttAndroidClient.connect(null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken iMqttToken) {
                    Log.e("action", "onSuccess");


                    MqttMessage msg = new MqttMessage();
                    msg.setPayload("android test".getBytes());
                    msg.setQos(2);
                    msg.setRetained(false);
                    try {
                        mqttAndroidClient.publish("yichao", msg);
                        Log.e("msg", "published");

                        mqttAndroidClient.subscribe("yichao", 2);


                        try{
                            mqttAndroidClient.disconnect();
                            mqttAndroidClient.close();
                            Log.e("client", "disconn & close");
                        }catch (MqttException e){
                            Log.e("disconn err", e.toString());
                        }


                    } catch (MqttPersistenceException e) {
                        Log.e("publish err", e.toString());
                    } catch (MqttException e){
                        Log.e("publish err", e.toString());
                    }
                }

                @Override
                public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                    Log.e("action", "onFailure");
                }
            });
            mqttAndroidClient.setCallback(this);
        } catch (MqttException e){
            Log.e("mqtt connect excp", e.toString());
        }

    }

    @Override
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.e("msg recv", message.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();





    }
}
