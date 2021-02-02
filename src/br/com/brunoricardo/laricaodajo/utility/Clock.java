package br.com.brunoricardo.laricaodajo.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Clock made of 6 of the Digit classes for hours, minutes and seconds.
 */
public class Clock extends Parent {

    private Calendar calendar = Calendar.getInstance();
    private Label lbHora;
    private Label lbData;
    private Timeline delayTimeline, secondTimeline;
    SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");

    public Clock(Label lbHora, Label lbData) {
        this.lbHora = lbHora;
        this.lbData = lbData;
        refreshClocks();
        play();
    }

    private void refreshClocks() {
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        lbHora.setText(hours + ":" + minutes + ":" + String.format("%02d", seconds));
        lbData.setText(ddMMyyyy.format(calendar.getTime()));
    }

    public void play() {
        delayTimeline = new Timeline();
        delayTimeline.getKeyFrames().add(
                new KeyFrame(new Duration(1000 - (System.currentTimeMillis() % 1000)), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (secondTimeline != null) {
                            secondTimeline.stop();
                        }
                        secondTimeline = new Timeline();
                        secondTimeline.setCycleCount(Timeline.INDEFINITE);
                        secondTimeline.getKeyFrames().add(
                                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        refreshClocks();
                                    }
                                }));
                        secondTimeline.play();
                    }
                })
        );
        delayTimeline.play();
    }

    public void stop() {
        delayTimeline.stop();
        if (secondTimeline != null) {
            secondTimeline.stop();
        }
    }
}
