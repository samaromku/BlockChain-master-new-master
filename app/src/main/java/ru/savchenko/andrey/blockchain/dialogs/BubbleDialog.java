package ru.savchenko.andrey.blockchain.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import lecho.lib.hellocharts.listener.BubbleChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.BubbleChartView;
import lecho.lib.hellocharts.view.Chart;
import ru.savchenko.andrey.blockchain.R;
import ru.savchenko.andrey.blockchain.repositories.BaseRepository;
import ru.savchenko.andrey.blockchain.entities.USD;

/**
 * Created by Andrey on 15.10.2017.
 */

public class BubbleDialog extends DialogFragment {
    private USD usd;
    private BubbleChartView chart;
    private BubbleChartData data;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;
    private static final int BUBBLES_NUM = 8;

    public void setUsd(USD usd) {
        this.usd = usd;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_buy_or_sell, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        chart = view.findViewById(R.id.chart);
        generateData();
    }

    private void generateData() {

        List<BubbleValue> values = new ArrayList<BubbleValue>();
        List<USD>usdList = new BaseRepository<>(USD.class).getAll();
        for (int i = 0; i < usdList.size(); i++) {
            float rawHeight = (float) (usdList.get(i).getLast() + 0);
            String dateStr = new SimpleDateFormat("HH.mm").format(usdList.get(i).getDate());
            BubbleValue value = new BubbleValue(i, Float.valueOf(dateStr), rawHeight);
            value.setColor(ChartUtils.pickColor());
            value.setShape(shape);
            values.add(value);
        }


        data = new BubbleChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setBubbleChartData(data);

    }

    private void setCircles() {
        shape = ValueShape.CIRCLE;
        generateData();
    }

    private void setSquares() {
        shape = ValueShape.SQUARE;
        generateData();
    }

    private void toggleLabels() {
        hasLabels = !hasLabels;

        if (hasLabels) {
            hasLabelForSelected = false;
            chart.setValueSelectionEnabled(hasLabelForSelected);
        }

        generateData();
    }

    private void toggleLabelForSelected() {
        hasLabelForSelected = !hasLabelForSelected;

        chart.setValueSelectionEnabled(hasLabelForSelected);

        if (hasLabelForSelected) {
            hasLabels = false;
        }

        generateData();
    }

    private void toggleAxes() {
        hasAxes = !hasAxes;

        generateData();
    }

    private void toggleAxesNames() {
        hasAxesNames = !hasAxesNames;

        generateData();
    }

    /**
     * To animate values you have to change targets values and then call {@link Chart#startDataAnimation()}
     * method(don't confuse with View.animate()).
     */
    private void prepareDataAnimation() {
        for (BubbleValue value : data.getValues()) {
            value.setTarget(value.getX() + (float) Math.random() * 4 * getSign(), (float) Math.random() * 100,
                    (float) Math.random() * 1000);
        }
    }

    private int getSign() {
        int[] sign = new int[]{-1, 1};
        return sign[Math.round((float) Math.random())];
    }

    private class ValueTouchListener implements BubbleChartOnValueSelectListener {

        @Override
        public void onValueSelected(int bubbleIndex, BubbleValue value) {
            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }
    }
}
