package ru.savchenko.andrey.blockchain.dialogs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.savchenko.andrey.blockchain.R;
import ru.savchenko.andrey.blockchain.repositories.BaseRepository;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.services.exchange.UpdateExchangeService;
import ru.savchenko.andrey.blockchain.storage.Prefs;

/**
 * Created by Andrey on 15.10.2017.
 */

public class SettingsDialog extends DialogFragment {
    @BindView(R.id.sbInterval)
    SeekBar sbInterval;
    @BindView(R.id.tvIntervalValue)
    TextView tvIntervalValue;
    @BindView(R.id.etCount)
    EditText etCount;
    @BindString(R.string.minutes)
    String minutes;
    private int interval = Prefs.getInterval();

    @OnClick(R.id.btnCancel)
    void onBtnCancel(){
        getDialog().dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.btnOk)
    void onBtnOk(){
        MoneyCount moneyCount = new BaseRepository<>(MoneyCount.class).getItem();
        moneyCount.setUsdCount(Double.valueOf(etCount.getText().toString()));
        if(Prefs.getInterval()!=sbInterval.getProgress()) {
            Prefs.writeInterval(sbInterval.getProgress());
            Intent intent = new Intent(getActivity(), UpdateExchangeService.class);
            getActivity().stopService(intent);
            getActivity().startService(UpdateExchangeService.newIntent(getActivity()));
        }
        getDialog().dismiss();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        sbInterval.setProgress(setProgressNotNull(interval));
        MoneyCount moneyCount = new BaseRepository<>(MoneyCount.class).getItem();
        if(moneyCount!=null) {
            etCount.setText(String.valueOf(moneyCount.getUsdCount()));
        }else {
            moneyCount = new MoneyCount(1, (double)1000, (double)0);
            new BaseRepository<>(MoneyCount.class).addItem(moneyCount);
            etCount.setText(String.valueOf(moneyCount.getUsdCount()));
        }
        sbInterval.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvIntervalValue.setText(String.format(minutes, i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(setProgressNotNull(seekBar.getProgress()));
            }
        });
    }

    private int setProgressNotNull(int progress) {
        if (progress < 3) {
            tvIntervalValue.setText(String.format(minutes, 3));
            return 3;
        } else {
            tvIntervalValue.setText(String.format(minutes, progress));
            return progress;
        }
    }
}
