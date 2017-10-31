package ru.savchenko.andrey.blockchain.repositories;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import ru.savchenko.andrey.blockchain.base.BaseAdapter;
import ru.savchenko.andrey.blockchain.entities.MoneyScore;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.interfaces.IUSDRepository;

import static ru.savchenko.andrey.blockchain.activities.MainActivity.TAG;

/**
 * Created by Andrey on 12.10.2017.
 */

public class USDRepository implements IUSDRepository{
    private Realm realmInstance() {
        return Realm.getDefaultInstance();
    }

    public void writeIdDb(USD usd) {
        BaseRepository baseRepository = new BaseRepository<>(USD.class);
        USD lastUsd = (USD) baseRepository.getLast();
        Log.i(TAG, "writeIdDb: " + lastUsd + " not last " + usd);
        if (!lastUsd.getLast().equals(usd.getLast())) {
            usd.setId(baseRepository.getMaxIdPlusOne());
            usd.setDate(new Date());
            baseRepository.addItem(usd);
        }
    }

    public int writeIdDbReturnInteger(USD usd) {
        BaseRepository baseRepository = new BaseRepository<>(USD.class);
        int maxId = baseRepository.getMaxIdPlusOne();
        USD lastUsd = (USD) baseRepository.getLast();
        Log.i(TAG, "writeIdDbReturnInteger: " + lastUsd + " not last " + usd);
        if (!lastUsd.getLast().equals(usd.getLast())) {
            usd.setId(maxId);
            usd.setDate(new Date());
            baseRepository.addItem(usd);
            return maxId;
        }
        return 0;
    }

    public void addChangeListener(BaseAdapter adapter, RecyclerView rvExchange) {
        realmInstance().addChangeListener(realm -> {
            adapter.notifyDataSetChanged();
            rvExchange.smoothScrollToPosition(adapter.getItemCount());
        });
        realmInstance().close();
    }

    private List<USD> getUSDListByDate(Date start, Date end) {
        List<USD> usds = realmInstance().where(USD.class)
                .between("date", start, end)
                .findAll();
        realmInstance().close();
        return usds;
    }

    public List<USD> getUSDByCalendarOneDayForward(Calendar calendar) {
        Date start = calendar.getTime();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, day + 1);
        Date end = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return getUSDListByDate(start, end);
    }

    public List<USD>getUSDFourHours(){
        Calendar calendar = Calendar.getInstance();
        Date end = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)-4);
        Date start = calendar.getTime();
        return getUSDListByDate(start, end);
    }

    public MoneyScore getMaxToday(Calendar calendar) {
        RealmResults<USD> todayList = (RealmResults<USD>) getUSDByCalendarOneDayForward(calendar);
        Double max = todayList.max("mLast").doubleValue();
        Double min = todayList.min("mLast").doubleValue();
        return new MoneyScore(1, max, min);
    }

    public MoneyScore getMaxFourHours(){
        RealmResults<USD> todayList = (RealmResults<USD>) getUSDFourHours();
        Double max = todayList.max("mLast").doubleValue();
        Double min = todayList.min("mLast").doubleValue();
        return new MoneyScore(1, max, min);
    }

    public MoneyScore getPreviousMaxToday(Calendar calendar) {
        RealmResults<USD> todayList = (RealmResults<USD>) getUSDByCalendarOneDayForward(calendar);
        List<USD> todaySorted = todayList.sort("mLast");
        if (todaySorted.size() > 1) {
            Double max = todaySorted.get(todaySorted.size() - 2).getLast();
            Double min = todaySorted.get(1).getLast();
            return new MoneyScore(1, max, min);
        } else return null;
    }

    public USD getPreLastUSD(Calendar calendar){
        RealmResults<USD> todayList = (RealmResults<USD>) getUSDByCalendarOneDayForward(calendar);
        for(USD usd:todayList){
            Log.i(TAG, "getPreLastUSD: " + usd);
        }
        return todayList.get(todayList.size()-2);
    }


    public Integer getMaxLast() {
        int maxLast = realmInstance().where(USD.class)
                .max("mLast")
                .intValue();
        realmInstance().close();
        return maxLast;
    }

    public USD getLastUSD() {
        int maxId = realmInstance().where(USD.class)
                .max("id")
                .intValue();


        USD lastUSD = new BaseRepository<>(USD.class).getItemById(maxId);
        realmInstance().close();
        return lastUSD;
    }

    public Integer getMintLast() {
        int minLast = realmInstance().where(USD.class)
                .min("mLast")
                .intValue();
        realmInstance().close();
        return minLast;
    }

    public List<USD> getLastFiveValues() {
        List<USD> usds = new ArrayList<>();
        List<USD> all = realmInstance().where(USD.class).findAllSorted("id", Sort.DESCENDING);
        realmInstance().close();
        for (int i = 0; i < 5; i++) {
            usds.add(all.get(i));
        }
        return usds;
    }

    @Override
    public void setBuyOrSell(USD lastUSD, int operation) {
        realmInstance().executeTransaction(realm -> lastUSD.setBuyOrSell(operation));
        realmInstance().close();
    }

    public List<USD> getUsdStartList() {
        List<USD> list = new ArrayList<>();
        list.add(new USD(594, 6001.08, 6001.63, 6001.08, 6000.52, "$", new Date(1508722909241L), 0, null));
        list.add(new USD(595, 6004.97, 6005.63, 6004.97, 6004.3, "$", new Date(1508724008658L), 0, null));
        list.add(new USD(596, 6015.98, 6016.64, 6015.98, 6015.31, "$", new Date(1508724548099L), 0, null));
        list.add(new USD(597, 5987.52, 5987.89, 5987.52, 5987.15, "$", new Date(1508725632365L), 1, 147.2800776749444));
        list.add(new USD(598, 5975.56, 5976.41, 5975.56, 5974.71, "$", new Date(1508726418399L), 0, null));
        list.add(new USD(599, 5961.23, 5962.01, 5961.23, 5960.44, "$", new Date(1508728124205L), 0, null));
        list.add(new USD(600, 5964.47, 5965.37, 5964.47, 5963.56, "$", new Date(1508728793968L), -1, 430.4651996777694));
        list.add(new USD(601, 5949.34, 5950.66, 5949.34, 5948.01, "$", new Date(1508729388537L), 0, null));
        list.add(new USD(602, 5963.91, 5965.19, 5963.91, 5962.62, "$", new Date(1508729878691L), 0, null));
        list.add(new USD(603, 5977.64, 5978.83, 5977.64, 5976.44, "$", new Date(1508730451510L), 0, null));
        list.add(new USD(604, 5971.86, 5974.21, 5971.86, 5969.51, "$", new Date(1508730714167L), 1, 288.8726386763569));
        list.add(new USD(605, 5961.99, 5964.08, 5961.99, 5959.89, "$", new Date(1508730971013L), 0, null));
        list.add(new USD(606, 5967.19, 5968.49, 5967.19, 5965.88, "$", new Date(1508731283838L), -1, 359.55125957608533));
        list.add(new USD(607, 5974.36, 5976.37, 5974.36, 5972.34, "$", new Date(1508731632724L), 0, null));
        list.add(new USD(608, 5967.62, 5967.9, 5967.62, 5967.34, "$", new Date(1508731982513L), 1, 324.21194912622116));
        list.add(new USD(609, 5957.67, 5959.91, 5957.67, 5955.43, "$", new Date(1508732228134L), 0, null));
        list.add(new USD(610, 5958.07, 5958.68, 5958.07, 5957.46, "$", new Date(1508732482783L), -1, 341.34429479371846));
        list.add(new USD(611, 5957.65, 5958.58, 5957.65, 5956.72, "$", new Date(1508732528685L), 0, null));
        list.add(new USD(612, 5965.44, 5967.41, 5965.44, 5963.48, "$", new Date(1508732828600L), 0, null));
        list.add(new USD(613, 5962.21, 5963.7, 5962.21, 5960.73, "$", new Date(1508733165221L), 0, null));
        list.add(new USD(614, 5965.24, 5967.11, 5965.24, 5963.37, "$", new Date(1508733418254L), 0, null));
        list.add(new USD(615, 5968.88, 5969.93, 5968.88, 5967.83, "$", new Date(1508733685663L), 0, null));
        list.add(new USD(616, 5972.13, 5972.95, 5972.13, 5971.3, "$", new Date(1508733928970L), 0, null));
        list.add(new USD(617, 5973.43, 5974.35, 5973.43, 5972.5, "$", new Date(1508734245149L), 0, null));
        list.add(new USD(618, 5995.78, 5996.87, 5995.78, 5994.69, "$", new Date(1508734520036L), 0, null));
        list.add(new USD(619, 5993.56, 5994.54, 5993.56, 5992.57, "$", new Date(1508734769920L), 1, 332.7781219599698));
        list.add(new USD(620, 5997.25, 5998.23, 5997.25, 5996.27, "$", new Date(1508735013115L), 0, null));
        list.add(new USD(621, 5995.05, 5996.8, 5995.05, 5993.29, "$", new Date(1508735259567L), 0, null));
        list.add(new USD(622, 5995.17, 5996.06, 5995.17, 5994.27, "$", new Date(1508735518916L), 0, null));
        list.add(new USD(623, 6045.74, 6046.13, 6045.74, 6045.35, "$", new Date(1508735771659L), 0, null));
        list.add(new USD(624, 6041.49, 6041.95, 6041.49, 6041.03, "$", new Date(1508736033178L), 1, 166.3890609799849));
        list.add(new USD(625, 6029.92, 6031.76, 6029.92, 6028.09, "$", new Date(1508736274115L), 0, null));
        list.add(new USD(626, 6042.76, 6044.22, 6042.76, 6041.3, "$", new Date(1508736753405L), -1, 423.94658340634464));
        list.add(new USD(627, 6043.14, 6043.95, 6043.14, 6042.33, "$", new Date(1508736993922L), 0, null));
        list.add(new USD(628, 6036.54, 6037.46, 6036.54, 6035.61, "$", new Date(1508737474229L), 1, 295.16782219316474));
        list.add(new USD(629, 6022.34, 6023.45, 6022.34, 6021.22, "$", new Date(1508737757980L), 0, null));
        list.add(new USD(630, 6009.39, 6010.87, 6009.39, 6007.9, "$", new Date(1508738054455L), 0, null));
        list.add(new USD(631, 5948.64, 5950.86, 5948.64, 5946.41, "$", new Date(1508738295321L), 0, null));
        list.add(new USD(632, 5936.36, 5937.24, 5936.36, 5935.47, "$", new Date(1508738569803L), 0, null));
        list.add(new USD(633, 5913.69, 5914.67, 5913.69, 5912.71, "$", new Date(1508738940658L), 0, null));
        list.add(new USD(634, 5897.57, 5898.14, 5897.57, 5896.99, "$", new Date(1508739287122L), 0, null));
        list.add(new USD(635, 5906.51, 5907.24, 5906.51, 5905.78, "$", new Date(1508739530636L), -1, 351.5832848685547));
        list.add(new USD(636, 5931.0, 5932.16, 5931.0, 5929.83, "$", new Date(1508739771025L), 0, null));
        list.add(new USD(637, 5934.54, 5935.6, 5934.54, 5933.48, "$", new Date(1508740103594L), 0, null));
        list.add(new USD(638, 5926.43, 5927.71, 5926.43, 5925.14, "$", new Date(1508740353315L), 1, 323.37555353085975));
        list.add(new USD(639, 5919.54, 5921.63, 5919.54, 5917.45, "$", new Date(1508740655080L), 0, null));
        list.add(new USD(640, 5899.95, 5900.36, 5899.95, 5899.55, "$", new Date(1508740895417L), 0, null));
        list.add(new USD(641, 5906.03, 5907.23, 5906.03, 5904.83, "$", new Date(1508741174451L), -1, 336.82705285193475));
        list.add(new USD(642, 5910.53, 5912.84, 5910.53, 5908.22, "$", new Date(1508741616159L), 0, null));
        list.add(new USD(643, 5888.98, 5889.76, 5888.98, 5888.2, "$", new Date(1508741893486L), 1, 330.1013031913973));
        list.add(new USD(644, 5867.83, 5869.75, 5867.83, 5865.91, "$", new Date(1508742232370L), 0, null));
        list.add(new USD(645, 5870.01, 5873.17, 5870.01, 5866.86, "$", new Date(1508742490520L), -1, 331.7394895119122));
        list.add(new USD(646, 5888.99, 5890.33, 5888.99, 5887.64, "$", new Date(1508742905052L), 0, null));
        list.add(new USD(647, 5874.84, 5875.6, 5874.84, 5874.09, "$", new Date(1508743206523L), 1, 330.92039635165474));
        list.add(new USD(648, 5866.43, 5867.73, 5866.43, 5865.13, "$", new Date(1508743451221L), 0, null));
        list.add(new USD(649, 5900.62, 5903.55, 5900.62, 5897.69, "$", new Date(1508743697924L), -1, 332.82364537986774));
        list.add(new USD(650, 5913.74, 5914.37, 5913.74, 5913.12, "$", new Date(1508744061946L), 0, null));
        list.add(new USD(651, 5916.83, 5917.48, 5916.83, 5916.18, "$", new Date(1508744302273L), 0, null));
        list.add(new USD(652, 5914.51, 5916.74, 5914.51, 5912.27, "$", new Date(1508744772443L), 1, 331.8720208657612));
        list.add(new USD(653, 5900.37, 5900.62, 5900.37, 5900.12, "$", new Date(1508745253123L), 0, null));
        list.add(new USD(654, 5885.97, 5887.34, 5885.97, 5884.59, "$", new Date(1508745601381L), 0, null));
        list.add(new USD(655, 5895.43, 5896.6, 5895.43, 5894.25, "$", new Date(1508745905472L), -1, 331.6200325731015));
        list.add(new USD(656, 5890.96, 5892.1, 5890.96, 5889.82, "$", new Date(1508746154411L), 0, null));
        list.add(new USD(657, 5880.11, 5881.67, 5880.11, 5878.55, "$", new Date(1508746411601L), 0, null));
        list.add(new USD(658, 5872.2, 5874.21, 5872.2, 5870.19, "$", new Date(1508746411728L), 0, null));
        list.add(new USD(659, 5872.61, 5873.98, 5872.61, 5871.23, "$", new Date(1508746656178L), -1, 165.162445081577));
        list.add(new USD(660, 5811.79, 5812.5, 5811.79, 5811.07, "$", new Date(1508747057357L), 0, null));
        list.add(new USD(661, 5821.18, 5822.12, 5821.18, 5820.23, "$", new Date(1508747301315L), 0, null));
        list.add(new USD(662, 5808.69, 5809.04, 5808.69, 5808.34, "$", new Date(1508747557962L), 0, null));
        list.add(new USD(663, 5831.37, 5833.28, 5831.37, 5829.46, "$", new Date(1508747813374L), 0, null));
        list.add(new USD(664, 5833.46, 5834.05, 5833.46, 5832.86, "$", new Date(1508748066973L), 0, null));
        list.add(new USD(665, 5837.74, 5838.21, 5837.74, 5837.26, "$", new Date(1508748352292L), 0, null));
        list.add(new USD(666, 5841.07, 5841.95, 5841.07, 5840.18, "$", new Date(1508748609029L), 0, null));
        list.add(new USD(667, 5822.11, 5822.97, 5822.11, 5821.26, "$", new Date(1508748919507L), 1, 414.3272492602199));
        list.add(new USD(668, 5825.03, 5825.54, 5825.03, 5824.51, "$", new Date(1508749196517L), 0, null));
        list.add(new USD(669, 5794.32, 5796.66, 5794.32, 5791.98, "$", new Date(1508749436859L), 0, null));
        list.add(new USD(670, 5784.83, 5785.71, 5784.83, 5783.95, "$", new Date(1508749677213L), 0, null));
        list.add(new USD(671, 5801.15, 5801.57, 5801.15, 5800.72, "$", new Date(1508749917533L), -1, 287.9615079616874));
        list.add(new USD(672, 5785.5, 5787.27, 5785.5, 5783.73, "$", new Date(1508750158085L), 0, null));
        list.add(new USD(673, 5813.12, 5815.44, 5813.12, 5810.79, "$", new Date(1508750398447L), 0, null));
        list.add(new USD(674, 5813.09, 5813.51, 5813.09, 5812.67, "$", new Date(1508750657061L), 0, null));
        list.add(new USD(675, 5818.97, 5819.39, 5818.97, 5818.55, "$", new Date(1508750920049L), 0, null));
        list.add(new USD(676, 5817.63, 5818.9, 5817.63, 5816.36, "$", new Date(1508751243038L), 0, null));
        list.add(new USD(677, 5817.38, 5817.56, 5817.38, 5817.2, "$", new Date(1508751492619L), 0, null));
        list.add(new USD(678, 5821.34, 5821.57, 5821.34, 5821.12, "$", new Date(1508751732961L), -1, 144.48710618905392));
        list.add(new USD(679, 5810.2, 5811.03, 5810.2, 5809.36, "$", new Date(1508752110386L), 0, null));
        list.add(new USD(680, 5816.35, 5817.37, 5816.35, 5815.33, "$", new Date(1508752433144L), 0, null));
        list.add(new USD(681, 5821.84, 5823.36, 5821.84, 5820.31, "$", new Date(1508752673489L), 0, null));
        list.add(new USD(682, 5860.95, 5861.98, 5860.95, 5859.92, "$", new Date(1508753154110L), 0, null));
        list.add(new USD(683, 5887.85, 5888.47, 5887.85, 5887.23, "$", new Date(1508753477115L), 0, null));
        list.add(new USD(684, 5876.55, 5878.58, 5876.55, 5874.51, "$", new Date(1508753746003L), 1, 423.3879317054806));
        list.add(new USD(685, 5863.66, 5864.51, 5863.66, 5862.81, "$", new Date(1508753987429L), 0, null));
        list.add(new USD(686, 5858.06, 5859.64, 5858.06, 5856.47, "$", new Date(1508754300593L), 0, null));
        list.add(new USD(687, 5858.75, 5859.66, 5858.75, 5857.83, "$", new Date(1508754707381L), -1, 283.64588209102374));
        list.add(new USD(688, 5860.3, 5860.57, 5860.3, 5860.02, "$", new Date(1508755188014L), 0, null));
        list.add(new USD(689, 5857.29, 5859.05, 5857.29, 5855.52, "$", new Date(1508755428942L), 1, 353.51690689825216));
        list.add(new USD(690, 5857.62, 5857.91, 5857.62, 5857.33, "$", new Date(1508755684183L), 0, null));
        list.add(new USD(691, 5866.4, 5867.64, 5866.4, 5865.15, "$", new Date(1508755924576L), 0, null));
        list.add(new USD(692, 5890.15, 5890.72, 5890.15, 5889.58, "$", new Date(1508756402074L), 0, null));
        list.add(new USD(693, 5870.42, 5870.89, 5870.42, 5869.95, "$", new Date(1508756693318L), 1, 176.75845344912608));
        list.add(new USD(694, 5878.05, 5880.0, 5878.05, 5876.1, "$", new Date(1508756949250L), 0, null));
        list.add(new USD(695, 5865.23, 5865.38, 5865.23, 5865.07, "$", new Date(1508757298171L), 0, null));
        list.add(new USD(696, 5878.9, 5880.02, 5878.9, 5877.78, "$", new Date(1508757654532L), 0, null));
        list.add(new USD(697, 5911.24, 5911.43, 5911.24, 5911.04, "$", new Date(1508757942413L), 0, null));
        list.add(new USD(698, 5893.16, 5895.16, 5893.16, 5891.16, "$", new Date(1508758224643L), 1, 88.37922672456304));
        list.add(new USD(699, 5907.25, 5907.51, 5907.25, 5906.98, "$", new Date(1508758569183L), 0, null));
        list.add(new USD(700, 5908.28, 5908.87, 5908.28, 5907.69, "$", new Date(1508758865473L), 0, null));
        list.add(new USD(701, 5897.25, 5898.57, 5897.25, 5895.92, "$", new Date(1508759106314L), 1, 44.18961336228152));
        list.add(new USD(702, 5896.5, 5897.21, 5896.5, 5895.8, "$", new Date(1508759347241L), 0, null));
        list.add(new USD(703, 5894.76, 5895.32, 5894.76, 5894.21, "$", new Date(1508759695660L), 0, null));
        list.add(new USD(704, 5887.28, 5888.49, 5887.28, 5886.06, "$", new Date(1508760068325L), 0, null));
        list.add(new USD(705, 5889.07, 5890.76, 5889.07, 5887.37, "$", new Date(1508760310219L), -1, 474.9623404068122));
        list.add(new USD(706, 5857.47, 5858.35, 5857.47, 5856.59, "$", new Date(1508760772145L), 0, null));
        list.add(new USD(707, 5843.56, 5844.34, 5843.56, 5842.77, "$", new Date(1508761013168L), 0, null));
        list.add(new USD(708, 5837.45, 5837.88, 5837.45, 5837.02, "$", new Date(1508761492059L), 0, null));
        list.add(new USD(709, 5845.05, 5846.25, 5845.05, 5843.85, "$", new Date(1508761763287L), -1, 235.72568676559735));
        list.add(new USD(710, 5809.61, 5810.16, 5809.61, 5809.05, "$", new Date(1508762145394L), 0, null));
        list.add(new USD(711, 5777.21, 5778.58, 5777.21, 5775.84, "$", new Date(1508762416463L), 0, null));
        list.add(new USD(712, 5774.31, 5774.55, 5774.31, 5774.07, "$", new Date(1508762768688L), 0, null));
        list.add(new USD(713, 5800.57, 5801.58, 5800.57, 5799.56, "$", new Date(1508763099442L), -1, 116.96957176675375));
        list.add(new USD(714, 5787.16, 5788.22, 5787.16, 5786.1, "$", new Date(1508763349118L), 0, null));
        list.add(new USD(715, 5769.99, 5770.37, 5769.99, 5769.61, "$", new Date(1508763702746L), 0, null));
        list.add(new USD(716, 5785.15, 5787.46, 5785.15, 5782.84, "$", new Date(1508764027974L), -1, 58.316175571565275));
        list.add(new USD(717, 5782.03, 5783.32, 5782.03, 5780.74, "$", new Date(1508764291143L), 0, null));
        list.add(new USD(718, 5781.55, 5781.89, 5781.55, 5781.21, "$", new Date(1508764606763L), 0, null));
        list.add(new USD(719, 5717.36, 5718.04, 5717.36, 5716.67, "$", new Date(1508764907649L), 0, null));
        list.add(new USD(720, 5725.66, 5727.22, 5725.66, 5724.09, "$", new Date(1508765150376L), -1, 28.8618600400012));
        list.add(new USD(721, 5685.22, 5686.46, 5685.22, 5683.97, "$", new Date(1508765522352L), 0, null));
        list.add(new USD(722, 5665.59, 5668.06, 5665.59, 5663.12, "$", new Date(1508765803873L), 0, null));
        list.add(new USD(723, 5674.76, 5676.69, 5674.76, 5672.84, "$", new Date(1508766049465L), -1, 14.30172430109593));
        list.add(new USD(724, 5683.0, 5684.42, 5683.0, 5681.57, "$", new Date(1508766297834L), 0, null));
        list.add(new USD(725, 5688.65, 5689.76, 5688.65, 5687.53, "$", new Date(1508766570142L), 0, null));
        list.add(new USD(726, 5698.0, 5698.66, 5698.0, 5697.35, "$", new Date(1508766852668L), 0, null));
        list.add(new USD(727, 5730.15, 5731.47, 5730.15, 5728.83, "$", new Date(1508767333299L), 0, null));
        list.add(new USD(728, 5749.66, 5750.35, 5749.66, 5748.97, "$", new Date(1508767583503L), 0, null));
        list.add(new USD(729, 5732.37, 5733.12, 5732.37, 5731.63, "$", new Date(1508767877139L), 1, 486.6634861070535));
        list.add(new USD(730, 5697.78, 5699.07, 5697.78, 5696.48, "$", new Date(1508768160242L), 0, null));
        list.add(new USD(731, 5707.87, 5708.34, 5707.87, 5707.39, "$", new Date(1508768414149L), -1, 249.43409441761182));
        list.add(new USD(732, 5716.86, 5717.66, 5716.86, 5716.05, "$", new Date(1508768660653L), 0, null));
        list.add(new USD(733, 5725.73, 5726.45, 5725.73, 5725.0, "$", new Date(1508768902389L), 0, null));
        list.add(new USD(734, 5747.75, 5748.68, 5747.75, 5746.81, "$", new Date(1508769218135L), 0, null));
        list.add(new USD(735, 5762.1, 5762.7, 5762.1, 5761.5, "$", new Date(1508769488140L), 0, null));
        list.add(new USD(736, 5750.8, 5751.14, 5750.8, 5750.46, "$", new Date(1508769800700L), 1, 368.04879026233266));
        list.add(new USD(737, 5715.04, 5716.07, 5715.04, 5714.01, "$", new Date(1508770041524L), 0, null));
        list.add(new USD(738, 5650.93, 5651.54, 5650.93, 5650.31, "$", new Date(1508770288612L), 0, null));
        list.add(new USD(739, 5670.18, 5672.43, 5670.18, 5667.94, "$", new Date(1508770536205L), -1, 305.2171615678449));
        list.add(new USD(740, 5687.56, 5688.59, 5687.56, 5686.54, "$", new Date(1508770778070L), 0, null));
        list.add(new USD(741, 5685.03, 5686.78, 5685.03, 5683.28, "$", new Date(1508771196507L), 1, 336.63297591508876));
        list.add(new USD(742, 5695.45, 5695.8, 5695.45, 5695.09, "$", new Date(1508771498373L), 0, null));
        list.add(new USD(743, 5693.67, 5695.07, 5693.67, 5692.28, "$", new Date(1508771798200L), 0, null));
        list.add(new USD(744, 5705.21, 5706.41, 5705.21, 5704.01, "$", new Date(1508772166415L), 0, null));
        list.add(new USD(745, 5718.22, 5719.46, 5718.22, 5716.97, "$", new Date(1508772406668L), 0, null));
        list.add(new USD(746, 5712.18, 5712.73, 5712.18, 5711.62, "$", new Date(1508772658803L), 1, 168.31648795754438));
        list.add(new USD(747, 5770.43, 5779.88, 5770.43, 5760.99, "$", new Date(1508772903294L), 0, null));
        list.add(new USD(748, 5780.07, 5780.64, 5780.07, 5779.5, "$", new Date(1508773247372L), 0, null));
        list.add(new USD(749, 5803.85, 5804.89, 5803.85, 5802.81, "$", new Date(1508773555986L), 0, null));
        list.add(new USD(750, 5797.6, 5799.29, 5797.6, 5795.92, "$", new Date(1508773819774L), 1, 84.15824397877219));
        list.add(new USD(751, 5779.0, 5779.89, 5779.0, 5778.1, "$", new Date(1508774290290L), 0, null));
        list.add(new USD(752, 5789.41, 5790.47, 5789.41, 5788.35, "$", new Date(1508774568164L), -1, 454.4453448723107));
        list.add(new USD(753, 5738.79, 5741.28, 5738.79, 5736.29, "$", new Date(1508774823922L), 0, null));
        list.add(new USD(754, 5759.12, 5760.41, 5759.12, 5757.82, "$", new Date(1508775079566L), 0, null));
        list.add(new USD(755, 5728.05, 5728.65, 5728.05, 5727.44, "$", new Date(1508775424614L), 0, null));
        list.add(new USD(756, 5748.48, 5749.95, 5748.48, 5747.01, "$", new Date(1508775862781L), 0, null));
        list.add(new USD(757, 5766.26, 5767.7, 5766.26, 5764.82, "$", new Date(1508776299953L), 0, null));
        list.add(new USD(758, 5753.45, 5754.54, 5753.45, 5752.36, "$", new Date(1508777126506L), 1, 269.30179442554146));
        list.add(new USD(759, 5795.18, 5795.6, 5795.18, 5794.75, "$", new Date(1508777631304L), 0, null));
        list.add(new USD(760, 5832.62, 5833.62, 5832.62, 5831.61, "$", new Date(1508777954359L), 0, null));
        list.add(new USD(761, 5851.48, 5853.5, 5851.48, 5849.45, "$", new Date(1508778197531L), 0, null));
        list.add(new USD(762, 5844.69, 5846.66, 5844.69, 5842.72, "$", new Date(1508778534617L), 0, null));
        list.add(new USD(763, 5845.21, 5846.34, 5845.21, 5844.08, "$", new Date(1508778780853L), 0, null));
        list.add(new USD(764, 5824.71, 5826.58, 5824.71, 5822.85, "$", new Date(1508779460620L), 0, null));
        list.add(new USD(765, 5839.21, 5840.05, 5839.21, 5838.38, "$", new Date(1508779999427L), 0, null));
        list.add(new USD(766, 5844.09, 5845.57, 5844.09, 5842.6, "$", new Date(1508780286838L), 0, null));
        list.add(new USD(767, 5839.07, 5839.75, 5839.07, 5838.39, "$", new Date(1508780842952L), 0, null));
        list.add(new USD(768, 5844.29, 5845.02, 5844.29, 5843.56, "$", new Date(1508781113635L), 0, null));
        list.add(new USD(769, 5846.01, 5846.61, 5846.01, 5845.42, "$", new Date(1508781491214L), 0, null));
        list.add(new USD(770, 5850.15, 5850.62, 5850.15, 5849.68, "$", new Date(1508781940379L), 0, null));
        list.add(new USD(771, 5856.84, 5858.0, 5856.84, 5855.69, "$", new Date(1508782638918L), 0, null));
        list.add(new USD(772, 5830.24, 5831.4, 5830.24, 5829.08, "$", new Date(1508783599184L), 0, null));
        list.add(new USD(773, 5796.59, 5798.26, 5796.59, 5794.92, "$", new Date(1508783879558L), 0, null));
        list.add(new USD(774, 5807.47, 5808.23, 5807.47, 5806.7, "$", new Date(1508784435065L), 0, null));
        list.add(new USD(775, 5810.22, 5810.65, 5810.22, 5809.8, "$", new Date(1508785091773L), 0, null));
        list.add(new USD(776, 5812.39, 5812.96, 5812.39, 5811.83, "$", new Date(1508785619702L), 0, null));
        list.add(new USD(777, 5812.43, 5813.24, 5812.43, 5811.63, "$", new Date(1508786034217L), 0, null));
        list.add(new USD(778, 5823.7, 5824.66, 5823.7, 5822.73, "$", new Date(1508786390567L), 0, null));
        list.add(new USD(779, 5859.08, 5859.47, 5859.08, 5858.69, "$", new Date(1508786881210L), 0, null));
        list.add(new USD(780, 5869.34, 5870.32, 5869.34, 5868.35, "$", new Date(1508787359837L), 0, null));
        list.add(new USD(781, 5872.04, 5872.76, 5872.04, 5871.32, "$", new Date(1508787731756L), 0, null));
        list.add(new USD(782, 5955.91, 5956.97, 5955.91, 5954.84, "$", new Date(1508788411563L), 0, null));
        list.add(new USD(783, 5926.43, 5926.88, 5926.43, 5925.99, "$", new Date(1508788801136L), 0, null));
        list.add(new USD(784, 5944.89, 5945.08, 5944.89, 5944.69, "$", new Date(1508789254399L), 0, null));
        list.add(new USD(785, 5952.45, 5953.99, 5952.45, 5950.91, "$", new Date(1508789542582L), 0, null));
        list.add(new USD(786, 5959.17, 5960.72, 5959.17, 5957.61, "$", new Date(1508789926450L), 0, null));
        list.add(new USD(787, 5976.25, 5977.49, 5976.25, 5975.0, "$", new Date(1508790492342L), 0, null));
        list.add(new USD(788, 5957.2, 5958.61, 5957.2, 5955.78, "$", new Date(1508790761137L), 0, null));
        list.add(new USD(789, 5936.75, 5936.97, 5936.75, 5936.53, "$", new Date(1508791164377L), 0, null));
        list.add(new USD(790, 5936.54, 5937.1, 5936.54, 5935.98, "$", new Date(1508791729293L), 0, null));
        list.add(new USD(791, 5940.01, 5940.53, 5940.01, 5939.49, "$", new Date(1508792004709L), 0, null));
        list.add(new USD(792, 5928.86, 5930.28, 5928.86, 5927.44, "$", new Date(1508792335612L), 0, null));
        list.add(new USD(793, 5912.95, 5914.25, 5912.95, 5911.65, "$", new Date(1508792603601L), 0, null));
        list.add(new USD(794, 5921.75, 5922.1, 5921.75, 5921.39, "$", new Date(1508792871165L), 0, null));
        list.add(new USD(795, 5927.34, 5927.73, 5927.34, 5926.96, "$", new Date(1508793203609L), 0, null));
        list.add(new USD(796, 5911.57, 5912.52, 5911.57, 5910.61, "$", new Date(1508793603063L), 0, null));
        list.add(new USD(797, 5909.64, 5911.82, 5909.64, 5907.45, "$", new Date(1508794060537L), 0, null));
        list.add(new USD(798, 5906.39, 5907.12, 5906.39, 5905.66, "$", new Date(1508794451193L), 0, null));
        list.add(new USD(799, 5617.84, 5618.33, 5617.84, 5617.36, "$", new Date(1508814001370L), -1, 703.9028862577769));
        list.add(new USD(800, 5638.56, 5640.0, 5638.56, 5637.12, "$", new Date(1508815008374L), -1, null));
        list.add(new USD(801, 5679.72, 5680.34, 5679.72, 5679.1, "$", new Date(1508815868204L), 0, null));
        list.add(new USD(802, 5660.1, 5661.28, 5660.1, 5658.92, "$", new Date(1508816414807L), 0, null));
        list.add(new USD(803, 5637.51, 5638.02, 5637.51, 5636.99, "$", new Date(1508816704562L), -1, null));
        list.add(new USD(804, 5653.32, 5655.02, 5653.32, 5651.62, "$", new Date(1508816961853L), 0, null));
        list.add(new USD(805, 5650.79, 5652.37, 5650.79, 5649.2, "$", new Date(1508817224044L), -1, null));
        list.add(new USD(806, 5633.94, 5634.49, 5633.94, 5633.38, "$", new Date(1508817962953L), -1, null));
        list.add(new USD(807, 5641.66, 5642.66, 5641.66, 5640.65, "$", new Date(1508818447498L), 0, null));
        list.add(new USD(808, 5626.87, 5627.4, 5626.87, 5626.33, "$", new Date(1508818876564L), 1, 973.2046806833184));
        list.add(new USD(809, 5666.34, 5667.22, 5666.34, 5665.46, "$", new Date(1508819230444L), 0, null));
        list.add(new USD(810, 5670.48, 5671.88, 5670.48, 5669.08, "$", new Date(1508819546477L), 0, null));
        list.add(new USD(811, 5650.7, 5651.72, 5650.7, 5649.68, "$", new Date(1508819954207L), 0, null));
        list.add(new USD(812, 5629.51, 5629.97, 5629.51, 5629.05, "$", new Date(1508820236928L), 0, null));
        list.add(new USD(813, 5624.31, 5624.74, 5624.31, 5623.87, "$", new Date(1508820507834L), 0, null));
        list.add(new USD(814, 5598.02, 5598.95, 5598.02, 5597.09, "$", new Date(1508820790538L), 1, null));
        list.add(new USD(815, 5597.55, 5599.64, 5597.55, 5595.46, "$", new Date(1508821099303L), 1, null));
        list.add(new USD(816, 5637.37, 5639.82, 5637.37, 5634.92, "$", new Date(1508821362633L), 0, null));
        list.add(new USD(817, 5657.05, 5657.83, 5657.05, 5656.26, "$", new Date(1508821631315L), 0, null));
        list.add(new USD(818, 5643.48, 5644.17, 5643.48, 5642.78, "$", new Date(1508821871615L), 0, null));
        list.add(new USD(819, 5643.43, 5644.06, 5643.43, 5642.8, "$", new Date(1508822112074L), 0, null));
        list.add(new USD(820, 5648.71, 5650.47, 5648.71, 5646.95, "$", new Date(1508822380107L), 0, null));
        list.add(new USD(821, 5633.44, 5634.35, 5633.44, 5632.53, "$", new Date(1508822701596L), 0, null));
        list.add(new USD(822, 5676.97, 5677.56, 5676.97, 5676.38, "$", new Date(1508823025478L), 0, null));
        list.add(new USD(823, 5674.68, 5675.22, 5674.68, 5674.13, "$", new Date(1508823387549L), 0, null));
        list.add(new USD(824, 5673.04, 5674.02, 5673.04, 5672.06, "$", new Date(1508823747580L), 0, null));
        list.add(new USD(825, 5682.35, 5683.38, 5682.35, 5681.31, "$", new Date(1508824107735L), 0, null));
        list.add(new USD(826, 5713.58, 5715.01, 5713.58, 5712.16, "$", new Date(1508824552698L), 0, null));
        list.add(new USD(827, 5716.22, 5717.17, 5716.22, 5715.26, "$", new Date(1508824827971L), 0, null));
        list.add(new USD(828, 5736.33, 5736.81, 5736.33, 5735.85, "$", new Date(1508825302430L), 0, null));
        list.add(new USD(829, 5739.92, 5740.43, 5739.92, 5739.41, "$", new Date(1508825549016L), 0, null));
        list.add(new USD(830, 5739.45, 5741.51, 5739.45, 5737.39, "$", new Date(1508825790179L), 0, null));
        list.add(new USD(831, 5767.18, 5767.81, 5767.18, 5766.55, "$", new Date(1508826031304L), 0, null));
        list.add(new USD(832, 5763.87, 5764.23, 5763.87, 5763.51, "$", new Date(1508826510217L), 0, null));
        list.add(new USD(833, 5760.27, 5761.29, 5760.27, 5759.25, "$", new Date(1508826752245L), 0, null));
        list.add(new USD(834, 5764.85, 5765.99, 5764.85, 5763.71, "$", new Date(1508827231318L), 0, null));
        list.add(new USD(835, 5752.63, 5754.22, 5752.63, 5751.03, "$", new Date(1508827472219L), 0, null));
        list.add(new USD(836, 5735.22, 5736.25, 5735.22, 5734.19, "$", new Date(1508827935301L), 0, null));
        list.add(new USD(837, 5708.93, 5709.29, 5708.93, 5708.57, "$", new Date(1508828177222L), 0, null));
        list.add(new USD(838, 5707.19, 5708.01, 5707.19, 5706.37, "$", new Date(1508828480022L), 0, null));
        list.add(new USD(839, 5718.17, 5718.91, 5718.17, 5717.44, "$", new Date(1508828911589L), 0, null));
        list.add(new USD(840, 5712.9, 5713.16, 5712.9, 5712.63, "$", new Date(1508829266592L), 0, null));
        list.add(new USD(841, 5711.81, 5712.43, 5711.81, 5711.18, "$", new Date(1508829602407L), 0, null));
        list.add(new USD(842, 5690.94, 5691.72, 5690.94, 5690.16, "$", new Date(1508830040590L), 0, null));
        list.add(new USD(843, 5688.37, 5688.78, 5688.37, 5687.95, "$", new Date(1508830281519L), 0, null));
        list.add(new USD(844, 5713.62, 5714.74, 5713.62, 5712.5, "$", new Date(1508830625651L), 0, null));
        list.add(new USD(845, 5741.11, 5743.97, 5741.11, 5738.25, "$", new Date(1508831034658L), 0, null));
        list.add(new USD(846, 5748.32, 5749.83, 5748.32, 5746.82, "$", new Date(1508831465707L), 0, null));
        list.add(new USD(847, 5731.66, 5732.39, 5731.66, 5730.92, "$", new Date(1508831955947L), 0, null));
        list.add(new USD(848, 5738.05, 5738.68, 5738.05, 5737.41, "$", new Date(1508832196394L), 0, null));
        list.add(new USD(849, 5750.12, 5750.56, 5750.12, 5749.67, "$", new Date(1508832517115L), 0, null));
        list.add(new USD(850, 5754.84, 5755.53, 5754.84, 5754.15, "$", new Date(1508832857729L), 0, null));
        list.add(new USD(851, 5736.66, 5736.88, 5736.66, 5736.44, "$", new Date(1508833175817L), 0, null));
        list.add(new USD(852, 5706.24, 5707.26, 5706.24, 5705.21, "$", new Date(1508833597715L), 0, null));
        list.add(new USD(853, 5713.12, 5713.98, 5713.12, 5712.25, "$", new Date(1508833839028L), 0, null));
        list.add(new USD(854, 5707.42, 5708.28, 5707.42, 5706.57, "$", new Date(1508834097685L), 0, null));
        list.add(new USD(855, 5675.03, 5675.36, 5675.03, 5674.69, "$", new Date(1508834366681L), 0, null));
        list.add(new USD(856, 5699.39, 5699.67, 5699.39, 5699.11, "$", new Date(1508834763232L), 0, null));
        list.add(new USD(857, 5704.6, 5705.0, 5704.6, 5704.2, "$", new Date(1508835038949L), 0, null));
        list.add(new USD(858, 5707.29, 5707.99, 5707.29, 5706.58, "$", new Date(1508835297303L), 0, null));
        list.add(new USD(859, 5696.8, 5697.34, 5696.8, 5696.27, "$", new Date(1508835552577L), 0, null));
        list.add(new USD(860, 5709.16, 5709.72, 5709.16, 5708.59, "$", new Date(1508836000065L), 0, null));
        list.add(new USD(861, 5716.91, 5717.86, 5716.91, 5715.96, "$", new Date(1508836242711L), 0, null));
        list.add(new USD(862, 5708.51, 5709.2, 5708.51, 5707.83, "$", new Date(1508836659173L), 0, null));
        list.add(new USD(863, 5696.31, 5698.24, 5696.31, 5694.37, "$", new Date(1508836912221L), 0, null));
        list.add(new USD(864, 5661.15, 5661.52, 5661.15, 5660.78, "$", new Date(1508837153151L), 0, null));
        list.add(new USD(865, 5665.42, 5666.39, 5665.42, 5664.45, "$", new Date(1508837424167L), 0, null));
        list.add(new USD(866, 5654.93, 5656.45, 5654.93, 5653.41, "$", new Date(1508837715287L), 0, null));
        list.add(new USD(867, 5654.82, 5655.16, 5654.82, 5654.48, "$", new Date(1508838051406L), 0, null));
        list.add(new USD(868, 5617.65, 5618.49, 5617.65, 5616.81, "$", new Date(1508838394164L), 0, null));
        list.add(new USD(869, 5633.03, 5633.69, 5633.03, 5632.36, "$", new Date(1508838650149L), 0, null));
        list.add(new USD(870, 5649.43, 5650.14, 5649.43, 5648.72, "$", new Date(1508838974693L), 0, null));
        list.add(new USD(871, 5655.43, 5656.2, 5655.43, 5654.67, "$", new Date(1508839216167L), 0, null));
        list.add(new USD(872, 5651.82, 5652.05, 5651.82, 5651.59, "$", new Date(1508839626488L), 0, null));
        list.add(new USD(873, 5666.87, 5667.71, 5666.87, 5666.03, "$", new Date(1508839872169L), 0, null));
        list.add(new USD(874, 5674.26, 5674.56, 5674.26, 5673.96, "$", new Date(1508840289855L), 0, null));
        list.add(new USD(875, 5658.79, 5660.05, 5658.79, 5657.54, "$", new Date(1508840530188L), 0, null));
        list.add(new USD(876, 5678.48, 5679.76, 5678.48, 5677.2, "$", new Date(1508841010626L), 0, null));
        list.add(new USD(877, 5665.69, 5666.56, 5665.69, 5664.81, "$", new Date(1508841491206L), 0, null));
        list.add(new USD(878, 5670.06, 5671.62, 5670.06, 5668.49, "$", new Date(1508841971734L), 0, null));
        list.add(new USD(879, 5666.93, 5667.17, 5666.93, 5666.7, "$", new Date(1508842355070L), 0, null));
        list.add(new USD(880, 5672.89, 5673.28, 5672.89, 5672.51, "$", new Date(1508842692472L), 0, null));
        list.add(new USD(881, 5701.46, 5702.62, 5701.46, 5700.29, "$", new Date(1508843173124L), 0, null));
        list.add(new USD(882, 5711.13, 5711.77, 5711.13, 5710.48, "$", new Date(1508843413400L), 0, null));
        list.add(new USD(883, 5699.7, 5700.78, 5699.7, 5698.61, "$", new Date(1508843653746L), 0, null));
        list.add(new USD(884, 5690.99, 5692.25, 5690.99, 5689.73, "$", new Date(1508843894140L), 0, null));
        list.add(new USD(885, 5680.75, 5681.48, 5680.75, 5680.02, "$", new Date(1508844134406L), 0, null));
        list.add(new USD(886, 5685.01, 5685.94, 5685.01, 5684.08, "$", new Date(1508844375220L), 0, null));
        list.add(new USD(887, 5700.96, 5701.42, 5700.96, 5700.5, "$", new Date(1508844855800L), 0, null));
        list.add(new USD(888, 5693.88, 5694.32, 5693.88, 5693.43, "$", new Date(1508845096233L), 0, null));
        list.add(new USD(889, 5685.97, 5686.51, 5685.97, 5685.43, "$", new Date(1508845336519L), 0, null));
        list.add(new USD(890, 5699.63, 5700.41, 5699.63, 5698.86, "$", new Date(1508845576861L), 0, null));
        list.add(new USD(891, 5701.77, 5702.42, 5701.77, 5701.12, "$", new Date(1508845817187L), 0, null));
        list.add(new USD(892, 5716.1, 5716.86, 5716.1, 5715.34, "$", new Date(1508846057560L), 0, null));
        list.add(new USD(893, 5711.81, 5713.26, 5711.81, 5710.36, "$", new Date(1508846529484L), 0, null));
        list.add(new USD(894, 5696.21, 5698.88, 5696.21, 5693.54, "$", new Date(1508846799905L), 0, null));
        list.add(new USD(895, 5676.2, 5677.68, 5676.2, 5674.72, "$", new Date(1508847234027L), 0, null));
        list.add(new USD(896, 5682.71, 5683.15, 5682.71, 5682.26, "$", new Date(1508847503991L), 0, null));
        list.add(new USD(897, 5682.18, 5683.39, 5682.18, 5680.98, "$", new Date(1508847954238L), 0, null));
        list.add(new USD(898, 5618.52, 5619.14, 5618.52, 5617.9, "$", new Date(1508848210678L), 0, null));
        list.add(new USD(899, 5633.87, 5634.93, 5633.87, 5632.82, "$", new Date(1508848466130L), 0, null));
        list.add(new USD(900, 5628.97, 5629.88, 5628.97, 5628.05, "$", new Date(1508848857019L), 0, null));
        list.add(new USD(901, 5626.92, 5627.42, 5626.92, 5626.43, "$", new Date(1508849159135L), 0, null));
        list.add(new USD(902, 5633.01, 5634.0, 5633.01, 5632.03, "$", new Date(1508849534194L), 0, null));
        list.add(new USD(903, 5643.43, 5645.14, 5643.43, 5641.72, "$", new Date(1508849819580L), 0, null));
        list.add(new USD(904, 5636.52, 5638.42, 5636.52, 5634.62, "$", new Date(1508850110901L), 0, null));
        list.add(new USD(905, 5644.21, 5644.87, 5644.21, 5643.54, "$", new Date(1508850405840L), 0, null));
        list.add(new USD(906, 5639.05, 5640.49, 5639.05, 5637.6, "$", new Date(1508850811889L), 0, null));
        list.add(new USD(907, 5612.51, 5613.16, 5612.51, 5611.86, "$", new Date(1508851141941L), 0, null));
        list.add(new USD(908, 5619.35, 5620.56, 5619.35, 5618.14, "$", new Date(1508851395043L), 0, null));
        list.add(new USD(909, 5612.87, 5613.93, 5612.87, 5611.8, "$", new Date(1508851728262L), 0, null));
        list.add(new USD(910, 5604.8, 5606.11, 5604.8, 5603.48, "$", new Date(1508851976079L), 0, null));
        list.add(new USD(911, 5607.26, 5608.58, 5607.26, 5605.94, "$", new Date(1508852224244L), 0, null));
        list.add(new USD(912, 5622.54, 5623.57, 5622.54, 5621.5, "$", new Date(1508852482220L), 0, null));
        list.add(new USD(913, 5676.62, 5679.0, 5676.62, 5674.24, "$", new Date(1508852958576L), 0, null));
        list.add(new USD(914, 5667.49, 5668.83, 5667.49, 5666.15, "$", new Date(1508853202457L), 0, null));
        list.add(new USD(915, 5654.74, 5656.36, 5654.74, 5653.11, "$", new Date(1508853587347L), 0, null));
        list.add(new USD(916, 5655.17, 5655.89, 5655.17, 5654.45, "$", new Date(1508853864350L), 0, null));
        list.add(new USD(917, 5663.73, 5664.3, 5663.73, 5663.16, "$", new Date(1508854120330L), 0, null));
        list.add(new USD(918, 5665.95, 5667.8, 5665.95, 5664.09, "$", new Date(1508854389418L), 0, null));
        list.add(new USD(919, 5647.3, 5647.76, 5647.3, 5646.84, "$", new Date(1508854837154L), 0, null));
        list.add(new USD(920, 5645.51, 5646.53, 5645.51, 5644.5, "$", new Date(1508855139065L), 0, null));
        list.add(new USD(921, 5651.05, 5652.4, 5651.05, 5649.69, "$", new Date(1508855381291L), 0, null));
        list.add(new USD(922, 5637.73, 5637.89, 5637.73, 5637.57, "$", new Date(1508855666154L), 0, null));
        list.add(new USD(923, 5627.32, 5627.48, 5627.32, 5627.16, "$", new Date(1508855941421L), 0, null));
        list.add(new USD(924, 5630.24, 5630.5, 5630.24, 5629.97, "$", new Date(1508855941833L), 0, null));
        list.add(new USD(925, 5637.66, 5637.98, 5637.66, 5637.33, "$", new Date(1508856208334L), 0, null));
        list.add(new USD(926, 5638.3, 5639.49, 5638.3, 5637.1, "$", new Date(1508856459531L), 0, null));
        list.add(new USD(927, 5632.14, 5632.94, 5632.14, 5631.34, "$", new Date(1508856731749L), 0, null));
        list.add(new USD(928, 5633.92, 5634.84, 5633.92, 5632.99, "$", new Date(1508856983595L), 0, null));
        list.add(new USD(929, 5636.94, 5637.38, 5636.94, 5636.5, "$", new Date(1508857316328L), 0, null));
        list.add(new USD(930, 5670.41, 5670.9, 5670.41, 5669.92, "$", new Date(1508857596658L), 0, null));
        list.add(new USD(931, 5668.87, 5669.32, 5668.87, 5668.41, "$", new Date(1508857596944L), 0, null));
        list.add(new USD(932, 5638.21, 5639.35, 5638.21, 5637.07, "$", new Date(1508858207744L), 0, null));
        list.add(new USD(933, 5635.3, 5635.98, 5635.3, 5634.61, "$", new Date(1508858456700L), 0, null));
        list.add(new USD(934, 5651.44, 5652.37, 5651.44, 5650.52, "$", new Date(1508859185331L), 0, null));
        list.add(new USD(935, 5640.95, 5642.9, 5640.95, 5639.0, "$", new Date(1508859510887L), 0, null));
        list.add(new USD(936, 5640.54, 5641.32, 5640.54, 5639.76, "$", new Date(1508859925259L), 0, null));
        list.add(new USD(937, 5637.52, 5638.64, 5637.52, 5636.39, "$", new Date(1508860252893L), 0, null));
        list.add(new USD(938, 5636.27, 5636.81, 5636.27, 5635.72, "$", new Date(1508860675824L), 0, null));
        list.add(new USD(939, 5638.46, 5639.14, 5638.46, 5637.78, "$", new Date(1508860970316L), 0, null));
        list.add(new USD(940, 5694.24, 5695.63, 5694.24, 5692.86, "$", new Date(1508861219684L), 0, null));
        list.add(new USD(941, 5717.12, 5717.74, 5717.12, 5716.5, "$", new Date(1508861465150L), 0, null));
        list.add(new USD(942, 5694.61, 5695.79, 5694.61, 5693.43, "$", new Date(1508861890648L), 0, null));
        list.add(new USD(943, 5699.78, 5700.44, 5699.78, 5699.11, "$", new Date(1508862202079L), 0, null));
        list.add(new USD(944, 5714.54, 5715.08, 5714.54, 5713.99, "$", new Date(1508862451533L), 0, null));
        list.add(new USD(945, 5708.41, 5709.02, 5708.41, 5707.8, "$", new Date(1508862696981L), 0, null));
        list.add(new USD(946, 5697.57, 5698.95, 5697.57, 5696.18, "$", new Date(1508862960199L), 0, null));
        list.add(new USD(947, 5698.43, 5699.15, 5698.43, 5697.71, "$", new Date(1508863219870L), 0, null));
        list.add(new USD(948, 5699.4, 5699.92, 5699.4, 5698.88, "$", new Date(1508863490068L), 0, null));
        list.add(new USD(949, 5719.86, 5721.12, 5719.86, 5718.61, "$", new Date(1508863887051L), 0, null));
        list.add(new USD(950, 5732.22, 5732.88, 5732.22, 5731.56, "$", new Date(1508864222430L), 0, null));
        list.add(new USD(951, 5722.77, 5723.34, 5722.77, 5722.19, "$", new Date(1508864623715L), 0, null));
        list.add(new USD(952, 5708.48, 5709.33, 5708.48, 5707.63, "$", new Date(1508864935699L), 0, null));
        list.add(new USD(953, 5702.89, 5703.28, 5702.89, 5702.5, "$", new Date(1508865392673L), 0, null));
        list.add(new USD(954, 5712.1, 5712.97, 5712.1, 5711.22, "$", new Date(1508865727504L), 0, null));
        list.add(new USD(955, 5708.52, 5708.98, 5708.52, 5708.07, "$", new Date(1508866175520L), 0, null));
        list.add(new USD(956, 5710.29, 5711.16, 5710.29, 5709.41, "$", new Date(1508867083763L), 0, null));
        list.add(new USD(957, 5713.28, 5713.93, 5713.28, 5712.62, "$", new Date(1508867662674L), 0, null));
        list.add(new USD(958, 5711.07, 5711.46, 5711.07, 5710.68, "$", new Date(1508867931102L), 0, null));
        list.add(new USD(959, 5704.26, 5705.69, 5704.26, 5702.84, "$", new Date(1508868507021L), 0, null));
        list.add(new USD(960, 5707.82, 5708.46, 5707.82, 5707.17, "$", new Date(1508868857183L), 0, null));
        list.add(new USD(961, 5707.77, 5708.31, 5707.77, 5707.22, "$", new Date(1508869117047L), 0, null));
        list.add(new USD(962, 5709.62, 5709.86, 5709.62, 5709.38, "$", new Date(1508869361022L), 0, null));
        list.add(new USD(963, 5711.79, 5712.12, 5711.79, 5711.46, "$", new Date(1508869759437L), 0, null));
        list.add(new USD(964, 5715.67, 5716.18, 5715.67, 5715.16, "$", new Date(1508870022849L), 0, null));
        list.add(new USD(965, 5718.54, 5718.94, 5718.54, 5718.13, "$", new Date(1508870329141L), 0, null));
        list.add(new USD(966, 5700.06, 5700.99, 5700.06, 5699.13, "$", new Date(1508870596371L), 0, null));
        list.add(new USD(967, 5705.54, 5705.96, 5705.54, 5705.11, "$", new Date(1508870838250L), 0, null));
        list.add(new USD(968, 5707.87, 5709.64, 5707.87, 5706.09, "$", new Date(1508871082738L), 0, null));
        list.add(new USD(969, 5709.36, 5710.35, 5709.36, 5708.36, "$", new Date(1508871445167L), 0, null));
        list.add(new USD(970, 5666.93, 5667.14, 5666.93, 5666.72, "$", new Date(1508872125430L), 0, null));
        list.add(new USD(971, 5649.43, 5650.35, 5649.43, 5648.5, "$", new Date(1508872748192L), 0, null));
        list.add(new USD(972, 5648.56, 5649.16, 5648.56, 5647.95, "$", new Date(1508872996892L), 0, null));
        list.add(new USD(973, 5660.64, 5662.22, 5660.64, 5659.05, "$", new Date(1508873313907L), 0, null));
        list.add(new USD(974, 5651.06, 5651.86, 5651.06, 5650.27, "$", new Date(1508873588162L), 0, null));
        list.add(new USD(975, 5651.96, 5652.33, 5651.96, 5651.58, "$", new Date(1508873969433L), 0, null));
        list.add(new USD(976, 5665.83, 5666.09, 5665.83, 5665.57, "$", new Date(1508874210935L), 0, null));
        list.add(new USD(977, 5672.39, 5673.07, 5672.39, 5671.71, "$", new Date(1508874638418L), 0, null));
        list.add(new USD(978, 5674.9, 5675.77, 5674.9, 5674.03, "$", new Date(1508874893079L), 1, 981.2688904889593));
        list.add(new USD(979, 5677.75, 5678.45, 5677.75, 5677.05, "$", new Date(1508875300822L), 0, null));
        list.add(new USD(980, 5664.9, 5665.37, 5664.9, 5664.42, "$", new Date(1508875673065L), 0, null));
        list.add(new USD(981, 5623.4, 5624.76, 5623.4, 5622.03, "$", new Date(1508876422271L), 0, null));
        list.add(new USD(982, 5621.43, 5622.6, 5621.43, 5620.26, "$", new Date(1508876722432L), 0, null));
        list.add(new USD(983, 5622.03, 5622.83, 5622.03, 5621.22, "$", new Date(1508877000128L), 0, null));
        list.add(new USD(984, 5571.77, 5572.39, 5571.77, 5571.15, "$", new Date(1508877570474L), 0, null));
        list.add(new USD(985, 5586.71, 5588.7, 5586.71, 5584.72, "$", new Date(1508877935168L), 0, null));
        list.add(new USD(986, 5579.27, 5580.39, 5579.27, 5578.14, "$", new Date(1508878240191L), 0, null));
        list.add(new USD(987, 5596.67, 5596.98, 5596.67, 5596.36, "$", new Date(1508878737308L), 0, null));
        list.add(new USD(988, 5588.54, 5590.48, 5588.54, 5586.6, "$", new Date(1508879138375L), 0, null));
        list.add(new USD(989, 5551.75, 5552.71, 5551.75, 5550.78, "$", new Date(1508879505999L), 1, null));
        list.add(new USD(990, 5546.0, 5547.73, 5546.0, 5544.27, "$", new Date(1508879881367L), 0, null));
        list.add(new USD(991, 5534.66, 5535.83, 5534.66, 5533.48, "$", new Date(1508880269682L), 0, null));
        list.add(new USD(992, 5502.86, 5503.63, 5502.86, 5502.09, "$", new Date(1508880695151L), 0, null));
        list.add(new USD(993, 5525.28, 5526.92, 5525.28, 5523.63, "$", new Date(1508880987728L), 0, null));
        list.add(new USD(994, 5506.33, 5506.88, 5506.33, 5505.77, "$", new Date(1508881298292L), 0, null));
        list.add(new USD(995, 5517.27, 5518.19, 5517.27, 5516.35, "$", new Date(1508881595347L), 0, null));
        list.add(new USD(996, 5500.89, 5501.79, 5500.89, 5499.98, "$", new Date(1508881996222L), 0, null));
        list.add(new USD(997, 5481.18, 5482.11, 5481.18, 5480.25, "$", new Date(1508882426245L), 0, null));
        list.add(new USD(998, 5470.66, 5471.09, 5470.66, 5470.24, "$", new Date(1508882673945L), 0, null));
        list.add(new USD(999, 5522.27, 5523.21, 5522.27, 5521.33, "$", new Date(1508882950015L), 0, null));
        list.add(new USD(1000, 5483.86, 5484.26, 5483.86, 5483.45, "$", new Date(1508883346321L), 0, null));
        list.add(new USD(1001, 5513.66, 5515.62, 5513.66, 5511.69, "$", new Date(1508883666301L), 0, null));
        list.add(new USD(1002, 5535.74, 5536.63, 5535.74, 5534.84, "$", new Date(1508884246626L), 0, null));
        list.add(new USD(1003, 5502.26, 5503.22, 5502.26, 5501.3, "$", new Date(1508885146386L), 0, null));
        list.add(new USD(1004, 5522.89, 5524.46, 5522.89, 5521.32, "$", new Date(1508885546275L), 0, null));
        list.add(new USD(1005, 5507.1, 5507.99, 5507.1, 5506.21, "$", new Date(1508886046507L), 0, null));
        list.add(new USD(1006, 5535.01, 5535.62, 5535.01, 5534.39, "$", new Date(1508887186368L), 0, null));
        list.add(new USD(1007, 5526.48, 5528.33, 5526.48, 5524.63, "$", new Date(1508887829194L), 0, null));
        list.add(new USD(1008, 5506.14, 5506.73, 5506.14, 5505.54, "$", new Date(1508888746646L), 0, null));
        list.add(new USD(1009, 5501.79, 5502.6, 5501.79, 5500.97, "$", new Date(1508889068253L), 0, null));
        list.add(new USD(1010, 5508.24, 5508.85, 5508.24, 5507.63, "$", new Date(1508889384816L), 0, null));
        list.add(new USD(1011, 5511.75, 5512.3, 5511.75, 5511.19, "$", new Date(1508889646278L), 0, null));
        list.add(new USD(1012, 5523.67, 5525.13, 5523.67, 5522.21, "$", new Date(1508890546592L), 0, null));
        list.add(new USD(1013, 5520.39, 5520.78, 5520.39, 5519.99, "$", new Date(1508890873648L), 0, null));
        list.add(new USD(1014, 5518.59, 5519.12, 5518.59, 5518.06, "$", new Date(1508891387875L), 0, null));
        list.add(new USD(1015, 5527.31, 5527.85, 5527.31, 5526.76, "$", new Date(1508891703143L), 0, null));
        list.add(new USD(1016, 5495.44, 5497.02, 5495.44, 5493.85, "$", new Date(1508892346739L), 0, null));
        list.add(new USD(1017, 5500.2, 5500.44, 5500.2, 5499.97, "$", new Date(1508892674021L), 0, null));
        list.add(new USD(1018, 5504.38, 5507.77, 5504.38, 5500.99, "$", new Date(1508892940840L), 0, null));
        list.add(new USD(1019, 5500.76, 5501.18, 5500.76, 5500.33, "$", new Date(1508893246798L), 0, null));
        list.add(new USD(1020, 5426.96, 5427.18, 5426.96, 5426.74, "$", new Date(1508894146813L), 0, null));
        list.add(new USD(1021, 5442.09, 5442.44, 5442.09, 5441.73, "$", new Date(1508894474802L), 0, null));
        list.add(new USD(1022, 5452.9, 5454.08, 5452.9, 5451.72, "$", new Date(1508894747567L), 0, null));
        list.add(new USD(1023, 5435.2, 5436.6, 5435.2, 5433.8, "$", new Date(1508895046334L), 0, null));
        list.add(new USD(1024, 5421.09, 5421.7, 5421.09, 5420.48, "$", new Date(1508895447010L), 0, null));
        list.add(new USD(1025, 5416.87, 5417.36, 5416.87, 5416.37, "$", new Date(1508895946589L), 0, null));
        list.add(new USD(1026, 5398.46, 5399.4, 5398.46, 5397.51, "$", new Date(1508896320950L), 0, null));
        list.add(new USD(1027, 5397.37, 5398.79, 5397.37, 5395.94, "$", new Date(1508896846580L), 0, null));
        list.add(new USD(1028, 5407.73, 5408.2, 5407.73, 5407.26, "$", new Date(1508897246340L), 0, null));
        list.add(new USD(1029, 5422.31, 5423.48, 5422.31, 5421.13, "$", new Date(1508897746555L), 0, null));
        list.add(new USD(1030, 5413.93, 5415.11, 5413.93, 5412.74, "$", new Date(1508897997375L), 0, null));
        list.add(new USD(1031, 5410.54, 5411.11, 5410.54, 5409.97, "$", new Date(1508898646413L), 0, null));
        list.add(new USD(1032, 5397.36, 5398.6, 5397.36, 5396.11, "$", new Date(1508899547785L), 0, null));
        list.add(new USD(1033, 5400.53, 5401.52, 5400.53, 5399.54, "$", new Date(1508899801928L), 0, null));
        list.add(new USD(1034, 5381.27, 5382.44, 5381.27, 5380.1, "$", new Date(1508900434139L), 0, null));
        list.add(new USD(1035, 5464.62, 5467.11, 5464.62, 5462.13, "$", new Date(1508901000994L), 0, null));
        list.add(new USD(1036, 5457.7, 5458.03, 5457.7, 5457.37, "$", new Date(1508901246123L), 0, null));
        list.add(new USD(1037, 5452.23, 5452.99, 5452.23, 5451.47, "$", new Date(1508901542255L), 0, null));
        list.add(new USD(1038, 5490.12, 5491.41, 5490.12, 5488.83, "$", new Date(1508902009079L), 0, null));
        list.add(new USD(1039, 5479.0, 5480.74, 5479.0, 5477.26, "$", new Date(1508902360853L), 0, null));
        list.add(new USD(1040, 5467.94, 5469.61, 5467.94, 5466.27, "$", new Date(1508902872304L), 0, null));
        list.add(new USD(1041, 5462.92, 5463.53, 5462.92, 5462.3, "$", new Date(1508903146626L), 0, null));
        list.add(new USD(1042, 5505.87, 5507.87, 5505.87, 5503.87, "$", new Date(1508903587175L), 0, null));
        list.add(new USD(1043, 5540.89, 5541.14, 5540.89, 5540.65, "$", new Date(1508904046674L), 0, null));
        list.add(new USD(1044, 5568.14, 5568.7, 5568.14, 5567.58, "$", new Date(1508904292486L), 0, null));
        list.add(new USD(1045, 5554.83, 5557.36, 5554.83, 5552.3, "$", new Date(1508904553221L), 0, null));
        list.add(new USD(1046, 5537.94, 5538.64, 5537.94, 5537.25, "$", new Date(1508904805571L), 0, null));
        list.add(new USD(1047, 5513.09, 5513.72, 5513.09, 5512.46, "$", new Date(1508905055943L), 0, null));
        list.add(new USD(1048, 5532.9, 5533.79, 5532.9, 5532.02, "$", new Date(1508905315356L), 0, null));
        list.add(new USD(1049, 5534.92, 5536.18, 5534.92, 5533.66, "$", new Date(1508905678747L), 0, null));
        list.add(new USD(1050, 5520.19, 5521.25, 5520.19, 5519.13, "$", new Date(1508906023058L), 0, null));
        list.add(new USD(1051, 5521.86, 5522.4, 5521.86, 5521.31, "$", new Date(1508906272373L), 0, null));
        list.add(new USD(1052, 5522.15, 5523.05, 5522.15, 5521.24, "$", new Date(1508906534695L), 0, null));
        list.add(new USD(1053, 5531.74, 5531.98, 5531.74, 5531.49, "$", new Date(1508906833402L), 0, null));
        list.add(new USD(1054, 5531.44, 5532.25, 5531.44, 5530.63, "$", new Date(1508907079717L), 0, null));
        list.add(new USD(1055, 5529.19, 5530.26, 5529.19, 5528.13, "$", new Date(1508907363396L), 0, null));
        list.add(new USD(1056, 5523.61, 5524.71, 5523.61, 5522.5, "$", new Date(1508907604151L), 0, null));
        list.add(new USD(1057, 5498.94, 5501.63, 5498.94, 5496.25, "$", new Date(1508907858657L), 0, null));
        list.add(new USD(1058, 5493.11, 5498.05, 5493.11, 5488.16, "$", new Date(1508908172096L), 0, null));
        list.add(new USD(1059, 5491.97, 5495.91, 5491.97, 5488.02, "$", new Date(1508908433150L), 0, null));
        list.add(new USD(1060, 5492.82, 5494.19, 5492.82, 5491.45, "$", new Date(1508908781117L), 0, null));
        list.add(new USD(1061, 5481.13, 5481.55, 5481.13, 5480.7, "$", new Date(1508909139437L), 0, null));
        list.add(new USD(1062, 5483.94, 5484.54, 5483.94, 5483.34, "$", new Date(1508909393733L), 0, null));
        list.add(new USD(1063, 5487.95, 5488.97, 5487.95, 5486.94, "$", new Date(1508909634096L), 0, null));
        list.add(new USD(1064, 5485.62, 5486.62, 5485.62, 5484.61, "$", new Date(1508909874723L), 0, null));
        list.add(new USD(1065, 5499.5, 5499.92, 5499.5, 5499.08, "$", new Date(1508910355007L), 0, null));
        list.add(new USD(1066, 5510.09, 5511.15, 5510.09, 5509.04, "$", new Date(1508910610848L), 0, null));
        list.add(new USD(1067, 5513.78, 5515.43, 5513.78, 5512.13, "$", new Date(1508911076653L), 0, null));
        list.add(new USD(1068, 5491.93, 5493.33, 5491.93, 5490.52, "$", new Date(1508911347064L), 0, null));
        list.add(new USD(1069, 5486.53, 5486.92, 5486.53, 5486.15, "$", new Date(1508911743435L), 0, null));
        list.add(new USD(1070, 5497.36, 5497.74, 5497.36, 5496.97, "$", new Date(1508912052852L), 0, null));
        list.add(new USD(1071, 5501.15, 5501.88, 5501.15, 5500.41, "$", new Date(1508912293301L), 0, null));
        list.add(new USD(1072, 5511.09, 5511.27, 5511.09, 5510.9, "$", new Date(1508912548540L), 0, null));
        list.add(new USD(1073, 5527.52, 5528.19, 5527.52, 5526.84, "$", new Date(1508912818838L), 0, null));
        list.add(new USD(1074, 5521.56, 5521.98, 5521.56, 5521.13, "$", new Date(1508913281191L), 0, null));
        list.add(new USD(1075, 5525.85, 5526.85, 5525.85, 5524.85, "$", new Date(1508913555953L), 0, null));
        list.add(new USD(1076, 5536.94, 5538.17, 5536.94, 5535.71, "$", new Date(1508914036484L), 0, null));
        list.add(new USD(1077, 5536.38, 5537.33, 5536.38, 5535.42, "$", new Date(1508914292500L), 0, null));
        list.add(new USD(1078, 5510.09, 5510.56, 5510.09, 5509.62, "$", new Date(1508914631039L), 0, null));
        list.add(new USD(1079, 5521.74, 5523.57, 5521.74, 5519.92, "$", new Date(1508915028783L), 0, null));
        list.add(new USD(1080, 5528.87, 5529.12, 5528.87, 5528.62, "$", new Date(1508915509291L), 0, null));
        list.add(new USD(1081, 5524.8, 5525.36, 5524.8, 5524.23, "$", new Date(1508915765576L), 0, null));
        list.add(new USD(1082, 5544.67, 5545.44, 5544.67, 5543.91, "$", new Date(1508916194601L), 0, null));
        list.add(new USD(1083, 5562.42, 5563.13, 5562.42, 5561.71, "$", new Date(1508916515150L), 0, null));
        list.add(new USD(1084, 5568.41, 5569.04, 5568.41, 5567.78, "$", new Date(1508916793734L), 0, null));
        list.add(new USD(1085, 5583.48, 5583.78, 5583.48, 5583.18, "$", new Date(1508917275842L), 0, null));
        list.add(new USD(1086, 5587.69, 5588.16, 5587.69, 5587.21, "$", new Date(1508917561241L), 0, null));
        list.add(new USD(1087, 5586.74, 5587.39, 5586.74, 5586.09, "$", new Date(1508917840061L), 0, null));
        list.add(new USD(1088, 5582.17, 5583.31, 5582.17, 5581.02, "$", new Date(1508918085562L), 0, null));
        list.add(new USD(1089, 5579.53, 5580.49, 5579.53, 5578.58, "$", new Date(1508918377012L), 0, null));
        list.add(new USD(1090, 5573.57, 5574.16, 5573.57, 5572.98, "$", new Date(1508918617368L), 0, null));
        list.add(new USD(1091, 5573.72, 5574.54, 5573.72, 5572.89, "$", new Date(1508919061759L), 0, null));
        list.add(new USD(1092, 5584.12, 5585.84, 5584.12, 5582.4, "$", new Date(1508919361614L), 0, null));
        list.add(new USD(1093, 5593.94, 5594.25, 5593.94, 5593.63, "$", new Date(1508919681059L), 0, null));
        list.add(new USD(1094, 5606.99, 5607.35, 5606.99, 5606.63, "$", new Date(1508919921436L), 0, null));
        list.add(new USD(1095, 5619.76, 5621.52, 5619.76, 5618.0, "$", new Date(1508920164290L), 0, null));
        list.add(new USD(1096, 5629.19, 5629.75, 5629.19, 5628.63, "$", new Date(1508920482286L), 0, null));
        list.add(new USD(1097, 5629.84, 5631.16, 5629.84, 5628.53, "$", new Date(1508920762009L), 0, null));
        list.add(new USD(1098, 5630.26, 5630.69, 5630.26, 5629.83, "$", new Date(1508921042071L), 0, null));
        list.add(new USD(1099, 5630.51, 5631.18, 5630.51, 5629.83, "$", new Date(1508921359925L), 0, null));
        list.add(new USD(1100, 5622.17, 5622.57, 5622.17, 5621.76, "$", new Date(1508921683962L), 0, null));
        list.add(new USD(1101, 5608.67, 5608.87, 5608.67, 5608.46, "$", new Date(1508921968739L), 0, null));
        list.add(new USD(1102, 5610.34, 5611.92, 5610.34, 5608.77, "$", new Date(1508922281289L), 0, null));
        list.add(new USD(1103, 5614.99, 5615.35, 5614.99, 5614.63, "$", new Date(1508922585166L), 0, null));
        list.add(new USD(1104, 5608.08, 5608.79, 5608.08, 5607.37, "$", new Date(1508922887076L), 0, null));
        list.add(new USD(1105, 5614.01, 5614.46, 5614.01, 5613.57, "$", new Date(1508923181322L), 0, null));
        list.add(new USD(1106, 5617.48, 5618.27, 5617.48, 5616.69, "$", new Date(1508923547000L), 0, null));
        list.add(new USD(1107, 5617.73, 5618.92, 5617.73, 5616.53, "$", new Date(1508923815325L), 0, null));
        list.add(new USD(1108, 5609.08, 5609.77, 5609.08, 5608.39, "$", new Date(1508924081338L), 0, null));
        list.add(new USD(1109, 5622.8, 5624.37, 5622.8, 5621.22, "$", new Date(1508924441668L), 0, null));
        list.add(new USD(1110, 5624.88, 5626.75, 5624.88, 5623.0, "$", new Date(1508924745084L), 0, null));
        list.add(new USD(1111, 5618.07, 5618.67, 5618.07, 5617.46, "$", new Date(1508925001654L), 0, null));
        list.add(new USD(1112, 5613.06, 5613.54, 5613.06, 5612.59, "$", new Date(1508925262011L), 0, null));
        list.add(new USD(1113, 5613.03, 5614.82, 5613.03, 5611.25, "$", new Date(1508925524077L), 0, null));
        list.add(new USD(1114, 5618.08, 5619.26, 5618.08, 5616.89, "$", new Date(1508925778294L), 0, null));
        list.add(new USD(1115, 5614.44, 5615.84, 5614.44, 5613.04, "$", new Date(1508926019564L), 0, null));
        list.add(new USD(1116, 5616.25, 5616.71, 5616.25, 5615.78, "$", new Date(1508926272669L), 0, null));
        list.add(new USD(1117, 5617.4, 5617.8, 5617.4, 5616.99, "$", new Date(1508926641476L), 0, null));
        list.add(new USD(1118, 5612.1, 5613.44, 5612.1, 5610.76, "$", new Date(1508926932879L), 0, null));
        list.add(new USD(1119, 5611.74, 5612.62, 5611.74, 5610.85, "$", new Date(1508927413222L), 0, null));
        list.add(new USD(1120, 5585.21, 5586.1, 5585.21, 5584.31, "$", new Date(1508927681805L), 0, null));
        list.add(new USD(1121, 5556.75, 5556.99, 5556.75, 5556.52, "$", new Date(1508928193125L), 0, null));
        list.add(new USD(1122, 5551.15, 5552.18, 5551.15, 5550.12, "$", new Date(1508928581088L), 0, null));
        list.add(new USD(1123, 5553.59, 5555.12, 5553.59, 5552.06, "$", new Date(1508928822384L), 0, null));
        list.add(new USD(1124, 5559.93, 5560.92, 5559.93, 5558.93, "$", new Date(1508929102912L), 0, null));
        list.add(new USD(1125, 5551.3, 5552.58, 5551.3, 5550.02, "$", new Date(1508929343255L), 0, null));
        list.add(new USD(1126, 5557.86, 5558.39, 5557.86, 5557.34, "$", new Date(1508929589547L), 0, null));
        list.add(new USD(1127, 5557.82, 5558.59, 5557.82, 5557.05, "$", new Date(1508929894924L), 0, null));
        list.add(new USD(1128, 5522.42, 5523.01, 5522.42, 5521.83, "$", new Date(1508930246372L), 0, null));
        list.add(new USD(1129, 5520.93, 5521.51, 5520.93, 5520.35, "$", new Date(1508930492918L), 0, null));
        list.add(new USD(1130, 5506.05, 5508.49, 5506.05, 5503.61, "$", new Date(1508930935250L), 0, null));
        list.add(new USD(1131, 5504.6, 5506.1, 5504.6, 5503.1, "$", new Date(1508931216490L), 0, null));
        list.add(new USD(1132, 5501.42, 5501.87, 5501.42, 5500.96, "$", new Date(1508931696760L), 0, null));
        list.add(new USD(1133, 5501.1, 5501.56, 5501.1, 5500.64, "$", new Date(1508932016046L), 0, null));
        list.add(new USD(1134, 5518.33, 5519.31, 5518.33, 5517.34, "$", new Date(1508932273436L), 0, null));
        list.add(new USD(1135, 5531.4, 5532.73, 5531.4, 5530.06, "$", new Date(1508932526182L), 0, null));
        list.add(new USD(1136, 5523.6, 5525.34, 5523.6, 5521.86, "$", new Date(1508932874192L), 0, null));
        list.add(new USD(1137, 5512.05, 5512.72, 5512.05, 5511.38, "$", new Date(1508933125995L), 0, null));
        list.add(new USD(1138, 5504.48, 5505.1, 5504.48, 5503.86, "$", new Date(1508933367112L), 0, null));
        list.add(new USD(1139, 5464.58, 5465.9, 5464.58, 5463.26, "$", new Date(1508933607612L), 0, null));
        list.add(new USD(1140, 5450.11, 5450.83, 5450.11, 5449.39, "$", new Date(1508933847971L), 0, null));
        list.add(new USD(1141, 5475.71, 5477.04, 5475.71, 5474.37, "$", new Date(1508934088279L), 0, null));
        list.add(new USD(1142, 5454.82, 5456.19, 5454.82, 5453.44, "$", new Date(1508934328635L), 0, null));
        list.add(new USD(1143, 5462.99, 5464.38, 5462.99, 5461.59, "$", new Date(1508934596945L), 0, null));
        list.add(new USD(1144, 5479.53, 5480.54, 5479.53, 5478.52, "$", new Date(1508934881800L), 0, null));
        list.add(new USD(1145, 5491.02, 5491.34, 5491.02, 5490.7, "$", new Date(1508935169022L), 0, null));
        list.add(new USD(1146, 5486.18, 5487.0, 5486.18, 5485.35, "$", new Date(1508935409479L), 0, null));
        list.add(new USD(1147, 5483.17, 5483.84, 5483.17, 5482.49, "$", new Date(1508935658275L), 0, null));
        list.add(new USD(1148, 5481.2, 5482.1, 5481.2, 5480.29, "$", new Date(1508935996004L), 0, null));
        list.add(new USD(1149, 5490.57, 5490.82, 5490.57, 5490.31, "$", new Date(1508936238206L), 0, null));
        list.add(new USD(1150, 5498.93, 5500.49, 5498.93, 5497.38, "$", new Date(1508936681860L), 0, null));
        list.add(new USD(1151, 5511.89, 5513.89, 5511.89, 5509.88, "$", new Date(1508936923602L), 0, null));
        list.add(new USD(1152, 5524.84, 5525.78, 5524.84, 5523.89, "$", new Date(1508937173398L), 0, null));
        list.add(new USD(1153, 5519.14, 5521.29, 5519.14, 5516.98, "$", new Date(1508937469908L), 0, null));
        list.add(new USD(1154, 5502.23, 5503.12, 5502.23, 5501.33, "$", new Date(1508937726916L), 0, null));
        list.add(new USD(1155, 5501.21, 5502.62, 5501.21, 5499.81, "$", new Date(1508938070191L), 0, null));
        list.add(new USD(1156, 5511.55, 5511.9, 5511.55, 5511.2, "$", new Date(1508938363456L), 0, null));
        list.add(new USD(1157, 5526.67, 5527.06, 5526.67, 5526.27, "$", new Date(1508938738312L), 0, null));
        list.add(new USD(1158, 5545.3, 5545.65, 5545.3, 5544.94, "$", new Date(1508939000497L), 0, null));
        list.add(new USD(1159, 5543.85, 5544.97, 5543.85, 5542.73, "$", new Date(1508939381472L), 0, null));
        list.add(new USD(1160, 5542.81, 5543.25, 5542.81, 5542.36, "$", new Date(1508939622137L), 0, null));
        list.add(new USD(1161, 5522.36, 5524.32, 5522.36, 5520.4, "$", new Date(1508939863303L), 0, null));
        list.add(new USD(1162, 5527.18, 5527.86, 5527.18, 5526.51, "$", new Date(1508940103796L), 0, null));
        list.add(new USD(1163, 5521.75, 5522.37, 5521.75, 5521.12, "$", new Date(1508940366383L), 0, null));
        list.add(new USD(1164, 5518.3, 5518.99, 5518.3, 5517.61, "$", new Date(1508940640218L), 0, null));
        list.add(new USD(1165, 5539.34, 5540.27, 5539.34, 5538.41, "$", new Date(1508941021015L), 0, null));
        list.add(new USD(1166, 5537.67, 5538.61, 5537.67, 5536.72, "$", new Date(1508941331320L), 0, null));
        list.add(new USD(1167, 5535.25, 5536.65, 5535.25, 5533.84, "$", new Date(1508941790283L), 0, null));
        list.add(new USD(1168, 5533.69, 5534.23, 5533.69, 5533.16, "$", new Date(1508942066292L), 0, null));
        list.add(new USD(1169, 5503.02, 5503.75, 5503.02, 5502.28, "$", new Date(1508942308101L), 0, null));
        list.add(new USD(1170, 5493.28, 5494.93, 5493.28, 5491.62, "$", new Date(1508942636797L), 0, null));
        list.add(new USD(1171, 5498.43, 5500.2, 5498.43, 5496.66, "$", new Date(1508942893189L), 0, null));
        list.add(new USD(1172, 5492.38, 5493.26, 5492.38, 5491.49, "$", new Date(1508943261760L), 0, null));
        list.add(new USD(1173, 5487.33, 5488.27, 5487.33, 5486.38, "$", new Date(1508943535029L), 0, null));
        list.add(new USD(1174, 5463.33, 5465.5, 5463.33, 5461.16, "$", new Date(1508944043241L), 0, null));
        list.add(new USD(1175, 5465.37, 5467.05, 5465.37, 5463.69, "$", new Date(1508944332710L), 0, null));
        list.add(new USD(1176, 5454.13, 5454.82, 5454.13, 5453.43, "$", new Date(1508945030060L), 0, null));
        list.add(new USD(1177, 5458.8, 5459.55, 5458.8, 5458.05, "$", new Date(1508945277239L), 0, null));
        list.add(new USD(1178, 5484.05, 5484.56, 5484.05, 5483.54, "$", new Date(1508945528522L), 0, null));
        list.add(new USD(1179, 5465.8, 5466.79, 5465.8, 5464.81, "$", new Date(1508945826203L), 0, null));
        list.add(new USD(1180, 5466.14, 5467.14, 5466.14, 5465.15, "$", new Date(1508946113522L), 0, null));
        list.add(new USD(1181, 5475.49, 5476.54, 5475.49, 5474.45, "$", new Date(1508946370935L), 0, null));
        list.add(new USD(1182, 5476.94, 5477.51, 5476.94, 5476.37, "$", new Date(1508946762917L), 0, null));
        return list;
    }
}
