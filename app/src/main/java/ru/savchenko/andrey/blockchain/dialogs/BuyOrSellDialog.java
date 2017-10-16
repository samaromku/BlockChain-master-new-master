package ru.savchenko.andrey.blockchain.dialogs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import ru.savchenko.andrey.blockchain.R;
import ru.savchenko.andrey.blockchain.base.BaseRepository;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.interfaces.OnRefreshAdapter;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;
import ru.savchenko.andrey.blockchain.storage.Utils;

import static ru.savchenko.andrey.blockchain.storage.Const.BUY_OPERATION;
import static ru.savchenko.andrey.blockchain.storage.Const.SELL_OPERATION;

/**
 * Created by savchenko on 14.10.17.
 */

public class BuyOrSellDialog extends DialogFragment{
    @BindView(R.id.tvBuyOrSell)TextView tvBuyOrSell;
    @BindView(R.id.rlDiagram)RelativeLayout rlDiagram;
    @BindView(R.id.btnOk)Button btnOk;
    @BindView(R.id.btnCancel)Button btnCancel;
    @BindView(R.id.etUSD)EditText etUSD;
    @BindView(R.id.etBitcoin)EditText etBitcoin;
    private USD usd;
    private LineChartView chart;
    private LineChartData data;
    private MoneyCount moneyCount = new BaseRepository<>(MoneyCount.class).getItem();
    private OnRefreshAdapter onRefreshAdapter;

    public void setOnRefreshAdapter(OnRefreshAdapter onRefreshAdapter) {
        this.onRefreshAdapter = onRefreshAdapter;
    }

    @OnClick(R.id.btnCancel)
    void sellUSD(){
        USD lastUsd = new USDRepository().getLastUSD();
        lastUsd.setBuyOrSell(SELL_OPERATION);
        onRefreshAdapter.refreshAdapter();
        Double usdToChange = Double.valueOf(etUSD.getText().toString());
        if(usdToChange>0) {
            Double btcValue = Double.valueOf(etBitcoin.getText().toString()) + usdToChange / lastUsd.getBuy();
            Double restUsd = moneyCount.getUsdCount() - usdToChange;
            etUSD.setText(String.valueOf(restUsd));
            etBitcoin.setText(String.valueOf(btcValue));
            moneyCount.setUsdCount(restUsd);
            moneyCount.setBitCoinCount(btcValue);
        }else {
            Toast.makeText(getActivity(), "Недостаточно средств", Toast.LENGTH_SHORT).show();
        }
    }
    
    @OnClick(R.id.btnOk)
    void buyUSD(){
        USD lastUsd = new USDRepository().getLastUSD();
        lastUsd.setBuyOrSell(BUY_OPERATION);
        onRefreshAdapter.refreshAdapter();
        Double btcToChange = Double.valueOf(etBitcoin.getText().toString());
        if(btcToChange>0) {
            Double usdValue = Double.valueOf(etUSD.getText().toString()) + btcToChange * lastUsd.getSell();
            Double restBtc = moneyCount.getBitCoinCount() - btcToChange;
            etBitcoin.setText(String.valueOf(restBtc));
            etUSD.setText(String.valueOf(usdValue));
            moneyCount.setUsdCount(usdValue);
            moneyCount.setBitCoinCount(restBtc);
        }else {
            Toast.makeText(getActivity(), "Недостаточно средств", Toast.LENGTH_SHORT).show();
        }

    }

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
        btnOk.setText("Продать B");
        btnCancel.setText("Продать $");
        tvBuyOrSell.setText(Utils.getBestAndWorstString(usd));
        chart = view.findViewById(R.id.chart);
        generateTempoData();
        etBitcoin.setText(String.valueOf(moneyCount.getBitCoinCount()));
        etUSD.setText(String.valueOf(moneyCount.getUsdCount()));
    }

    private void generateTempoData() {
        // I got speed in range (0-50) and height in meters in range(200 - 300). I want this chart to display both
        // information. Differences between speed and height values are large and chart doesn't look good so I need
        // to modify height values to be in range of speed values.

        // The same for displaying Tempo/Height chart.

        float minHeight = new USDRepository().getMaxLast();
        float maxHeight = new USDRepository().getMintLast();
        float tempoRange = 5f; // from 0min/km to 15min/km

        float scale = tempoRange / maxHeight * 8;
        float sub = (float) ((minHeight * scale) /1.1);

        Line line;
        List<PointValue> values;
        List<Line> lines = new ArrayList<>();

        // Height line, add it as first line to be drawn in the background.
        values = new ArrayList<>();
        List<AxisValue>axisValues = new ArrayList<>();
        List<USD>usdList = Utils.getUSDListByDate(usd.getDate());
        for (int i = 0; i < usdList.size(); i++) {
            float rawHeight = (float) (usdList.get(i).getLast() + 0);
            float normalizedHeight = rawHeight * scale - sub;
            String dateStr = new SimpleDateFormat("HH.mm").format(usdList.get(i).getDate());
            values.add(new PointValue(Float.valueOf(dateStr), normalizedHeight));
            axisValues.add(new AxisValue(Float.valueOf(dateStr)));
        }

        line = new Line(values);

        line.setColor(Color.RED);
        line.setHasPoints(false);
        line.setFilled(true);
        line.setStrokeWidth(1);
        lines.add(line);

        // Data and axes
        data = new LineChartData(lines);

        Axis distanceAxis = new Axis();
        distanceAxis.setName("Время");
        distanceAxis.setTextColor(ChartUtils.COLOR_ORANGE);
//        distanceAxis.setMaxLabelChars(4);
//        distanceAxis.setFormatter(new SimpleAxisValueFormatter().setAppendedText("мин".toCharArray()));
        distanceAxis.setHasLines(true);
        distanceAxis.setHasTiltedLabels(true);
        distanceAxis.setValues(axisValues);
        data.setAxisXBottom(distanceAxis);

        data.setAxisYRight(new Axis().setName("Цена в $").setMaxLabelChars(3).setTextColor(ChartUtils.COLOR_RED)
                .setFormatter(new HeightValueFormatter(scale, sub, 0)));

        // Set data
        chart.setLineChartData(data);

        // Important: adjust viewport, you could skip this step but in this case it will looks better with custom
        // viewport. Set
        // viewport with Y range 0-12;
        Viewport v = chart.getMaximumViewport();
        v.set(v.left, tempoRange, v.right, 0);
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);

    }

    private String formatMinutes(float value) {
        StringBuilder sb = new StringBuilder();

        // translate value to seconds, for example
        int valueInSeconds = (int) (value * 60);
        int minutes = (int) Math.floor(valueInSeconds / 60);
        int seconds = (int) valueInSeconds % 60;

        sb.append(String.valueOf(minutes)).append(':');
        if (seconds < 10) {
            sb.append('0');
        }
        sb.append(String.valueOf(seconds));
        return sb.toString();
    }

    /**
     * Recalculated height values to display on axis. For this example I use auto-generated height axis so I
     * override only formatAutoValue method.
     */
    private static class HeightValueFormatter extends SimpleAxisValueFormatter {

        private float scale;
        private float sub;
        private int decimalDigits;

        public HeightValueFormatter(float scale, float sub, int decimalDigits) {
            this.scale = scale;
            this.sub = sub;
            this.decimalDigits = decimalDigits;
        }

        @Override
        public int formatValueForAutoGeneratedAxis(char[] formattedValue, float value, int autoDecimalDigits) {
            float scaledValue = (value + sub) / scale;
            return super.formatValueForAutoGeneratedAxis(formattedValue, scaledValue, this.decimalDigits);
        }
    }
}
