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
import ru.savchenko.andrey.blockchain.base.BaseRepository;
import ru.savchenko.andrey.blockchain.entities.MoneyScore;
import ru.savchenko.andrey.blockchain.entities.USD;

import static ru.savchenko.andrey.blockchain.activities.MainActivity.TAG;

/**
 * Created by Andrey on 12.10.2017.
 */

public class USDRepository {
    private Realm realmInstance() {
        return Realm.getDefaultInstance();
    }

    public void writeIdDb(USD usd){
        BaseRepository baseRepository = new BaseRepository<>(USD.class);
        USD lastUsd = (USD) baseRepository.getLast();
        Log.i(TAG, "writeIdDb: " + lastUsd + " not last " + usd);
        if(!lastUsd.getLast().equals(usd.getLast())) {
            usd.setId(baseRepository.getMaxIdPlusOne());
            usd.setDate(new Date());
            baseRepository.addItem(usd);
        }
    }

    public int writeIdDbReturnInteger(USD usd){
        BaseRepository baseRepository = new BaseRepository<>(USD.class);
        int maxId = baseRepository.getMaxIdPlusOne();
        USD lastUsd = (USD) baseRepository.getLast();
        Log.i(TAG, "writeIdDbReturnInteger: " + lastUsd + " not last " + usd);
        if(!lastUsd.getLast().equals(usd.getLast())) {
            usd.setId(maxId);
            usd.setDate(new Date());
            baseRepository.addItem(usd);
            return maxId;
        }return 0;
    }

    public void addChangeListener(BaseAdapter adapter, RecyclerView rvExchange){
        realmInstance().addChangeListener(realm -> {
            adapter.notifyDataSetChanged();
            rvExchange.smoothScrollToPosition(adapter.getItemCount());
        });
        realmInstance().close();
    }

    private List<USD>getUSDListByDate(Date start, Date end){
        List<USD>usds = realmInstance().where(USD.class)
                .between("date", start, end)
                .findAll();
        realmInstance().close();
        return usds;
    }

    public List<USD> getUSDByCalendarOneDayForward(Calendar calendar){
        Date start = calendar.getTime();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, day + 1);
        Date end = calendar.getTime();

        return getUSDListByDate(start, end);
    }

    public MoneyScore getMaxToday(Calendar calendar){
        RealmResults<USD> todayList = (RealmResults<USD>) getUSDByCalendarOneDayForward(calendar);
        Double max =todayList.max("mLast").doubleValue();
        Double min = todayList.min("mLast").doubleValue();
        return new MoneyScore(1, max, min);
    }


    public Integer getMaxLast(){
        int maxLast = realmInstance().where(USD.class)
                .max("mLast")
                .intValue();
        realmInstance().close();
        return maxLast;
    }

    public USD getLastUSD(){
        int maxId = realmInstance().where(USD.class)
                .max("id")
                .intValue();


        USD lastUSD = new BaseRepository<>(USD.class).getItemById(maxId);
        realmInstance().close();
        return lastUSD;
    }

    public Integer getMintLast(){
        int minLast = realmInstance().where(USD.class)
                .min("mLast")
                .intValue();
        realmInstance().close();
        return minLast;
    }

    public List<USD> getLastFiveValues(){
        List<USD>usds = new ArrayList<>();
        List<USD>all = realmInstance().where(USD.class).findAllSorted("id", Sort.DESCENDING);
        realmInstance().close();
        for (int i = 0; i < 5; i++) {
            usds.add(all.get(i));
        }
        return usds;
    }

    public List<USD>getUsdStartList(){
        List<USD>list = new ArrayList<>();
        list.add(new USD(1, 5524.71, 5525.11, 5524.71, 5524.3, "$", new Date(1508079095375L),0, null));
        list.add(new USD(2, 5551.9, 5553.15, 5551.9, 5550.65, "$", new Date(1508080971617L),0, null));
        list.add(new USD(3, 5553.38, 5554.64, 5553.38, 5552.11, "$", new Date(1508081119786L),0, null));
        list.add(new USD(4, 5553.38, 5554.64, 5553.38, 5552.11, "$", new Date(1508081129600L),0, null));
        list.add(new USD(5, 5558.91, 5559.71, 5558.91, 5558.1, "$", new Date(1508081492552L),0, null));
        list.add(new USD(6, 5544.01, 5544.4, 5544.01, 5543.62, "$", new Date(1508082392368L),0, null));
        list.add(new USD(7, 5532.5, 5533.35, 5532.5, 5531.65, "$", new Date(1508083292496L),0, null));
        list.add(new USD(8, 5538.89, 5540.24, 5538.89, 5537.54, "$", new Date(1508084192411L),0, null));
        list.add(new USD(9, 5531.39, 5531.8, 5531.39, 5530.98, "$", new Date(1508085092642L),0, null));
        list.add(new USD(10, 5501.19, 5501.66, 5501.19, 5500.72, "$", new Date(1508085992417L),0, null));
        list.add(new USD(11, 5496.33, 5496.62, 5496.33, 5496.04, "$", new Date(1508086892367L),0, null));
        list.add(new USD(12, 5510.71, 5510.86, 5510.71, 5510.55, "$", new Date(1508087792431L),0, null));
        list.add(new USD(13, 5507.99, 5508.37, 5507.99, 5507.62, "$", new Date(1508088352698L),0, null));
        list.add(new USD(14, 5510.4, 5510.92, 5510.4, 5509.87, "$", new Date(1508088692682L),0, null));
        list.add(new USD(15, 5513.54, 5515.17, 5513.54, 5511.91, "$", new Date(1508089592418L),0, null));
        list.add(new USD(16, 5524.56, 5525.16, 5524.56, 5523.95, "$", new Date(1508091442329L),0, null));
        list.add(new USD(17, 5507.36, 5508.18, 5507.36, 5506.53, "$", new Date(1508093241987L),0, null));
        list.add(new USD(18, 5529.14, 5529.86, 5529.14, 5528.41, "$", new Date(1508095041820L),0, null));
        list.add(new USD(19, 5685.93, 5686.26, 5685.93, 5685.59, "$", new Date(1508120091340L),0, null));
        list.add(new USD(20, 5705.98, 5707.18, 5705.98, 5704.78, "$", new Date(1508121889533L),0, null));
        list.add(new USD(21, 5688.68, 5690.15, 5688.68, 5687.21, "$", new Date(1508123238513L),0, null));
        list.add(new USD(22, 5692.04, 5692.96, 5692.04, 5691.11, "$", new Date(1508123689536L),0, null));
        list.add(new USD(23, 5632.58, 5633.78, 5632.58, 5631.37, "$", new Date(1508125489566L),0, null));
        list.add(new USD(24, 5621.42, 5621.99, 5621.42, 5620.85, "$", new Date(1508126164630L),0, null));
        list.add(new USD(25, 5622.0, 5622.9, 5622.0, 5621.09, "$", new Date(1508127289609L),0, null));
        list.add(new USD(26, 5630.26, 5630.86, 5630.26, 5629.67, "$", new Date(1508129089691L),0, null));
        list.add(new USD(27, 5603.68, 5605.22, 5603.68, 5602.14, "$", new Date(1508130889625L),0, null));
        list.add(new USD(28, 5678.5, 5679.98, 5678.5, 5677.02, "$", new Date(1508132689766L),0, null));
        list.add(new USD(29, 5656.97, 5658.26, 5656.97, 5655.67, "$", new Date(1508134041045L),0, null));
        list.add(new USD(30, 5645.73, 5646.32, 5645.73, 5645.13, "$", new Date(1508134422419L),0, null));
        list.add(new USD(31, 5643.01, 5643.5, 5643.01, 5642.52, "$", new Date(1508134489403L),0, null));
        list.add(new USD(32, 5623.84, 5624.95, 5623.84, 5622.73, "$", new Date(1508135385787L),0, null));
        list.add(new USD(33, 5630.01, 5630.92, 5630.01, 5629.1, "$", new Date(1508136289557L),0, null));
        list.add(new USD(34, 5648.21, 5650.2, 5648.21, 5646.21, "$", new Date(1508137962348L),0, null));
        list.add(new USD(35, 5647.53, 5649.22, 5647.53, 5645.83, "$", new Date(1508138089591L),0, null));
        list.add(new USD(36, 5610.03, 5611.75, 5610.03, 5608.32, "$", new Date(1508139889598L),0, null));
        list.add(new USD(37, 5630.88, 5631.26, 5630.88, 5630.5, "$", new Date(1508141025095L),0, null));
        list.add(new USD(38, 5625.78, 5626.85, 5625.78, 5624.7, "$", new Date(1508141276892L),0, null));
        list.add(new USD(39, 5617.81, 5618.29, 5617.81, 5617.32, "$", new Date(1508141689657L),0, null));
        list.add(new USD(40, 5611.54, 5612.21, 5611.54, 5610.86, "$", new Date(1508143494683L),0, null));
        list.add(new USD(41, 5615.71, 5617.06, 5615.71, 5614.36, "$", new Date(1508143636257L),0, null));
        list.add(new USD(42, 5599.27, 5599.71, 5599.27, 5598.82, "$", new Date(1508145289899L),0, null));
        list.add(new USD(43, 5587.47, 5588.32, 5587.47, 5586.62, "$", new Date(1508145699338L),0, null));
        list.add(new USD(44, 5583.15, 5583.33, 5583.15, 5582.97, "$", new Date(1508145711763L),0, null));
        list.add(new USD(45, 5583.15, 5583.33, 5583.15, 5582.97, "$", new Date(1508145762094L),0, null));
        list.add(new USD(46, 5613.46, 5614.6, 5613.46, 5612.31, "$", new Date(1508147089776L),0, null));
        list.add(new USD(47, 5650.58, 5651.55, 5650.58, 5649.61, "$", new Date(1508147886344L),0, null));
        list.add(new USD(48, 5635.45, 5635.68, 5635.45, 5635.21, "$", new Date(1508148704608L),0, null));
        list.add(new USD(49, 5641.23, 5643.92, 5641.23, 5638.55, "$", new Date(1508148889597L),0, null));
        list.add(new USD(50, 5709.92, 5711.11, 5709.92, 5708.74, "$", new Date(1508150689950L),0, null));
        list.add(new USD(51, 5707.51, 5708.0, 5707.51, 5707.03, "$", new Date(1508151917225L),0, null));
        list.add(new USD(52, 5714.46, 5714.49, 5714.46, 5714.42, "$", new Date(1508152489528L),0, null));
        list.add(new USD(53, 5722.56, 5723.2, 5722.56, 5721.91, "$", new Date(1508154289813L),0, null));
        list.add(new USD(54, 5748.66, 5749.2, 5748.66, 5748.11, "$", new Date(1508155364991L),0, null));
        list.add(new USD(55, 5738.66, 5739.01, 5738.66, 5738.3, "$", new Date(1508156089795L),0, null));
        list.add(new USD(56, 5738.52, 5739.13, 5738.52, 5737.92, "$", new Date(1508157889760L),0, null));
        list.add(new USD(57, 5720.79, 5721.21, 5720.79, 5720.36, "$", new Date(1508158929986L),0, null));
        list.add(new USD(58, 5710.56, 5710.62, 5710.56, 5710.5, "$", new Date(1508159456239L),0, null));
        list.add(new USD(59, 5710.56, 5710.62, 5710.56, 5710.5, "$", new Date(1508159469681L),0, null));
        list.add(new USD(60, 5707.04, 5708.06, 5707.04, 5706.01, "$", new Date(1508159551336L),0, null));
        list.add(new USD(61, 5713.37, 5713.73, 5713.37, 5713.0, "$", new Date(1508159689613L),0, null));
        list.add(new USD(62, 5712.6, 5713.13, 5712.6, 5712.07, "$", new Date(1508159711994L),0, null));
        list.add(new USD(63, 5713.37, 5713.73, 5713.37, 5713.0, "$", new Date(1508159870297L),0, null));
        list.add(new USD(64, 5684.36, 5684.71, 5684.36, 5684.01, "$", new Date(1508160925249L),0, null));
        list.add(new USD(65, 5686.91, 5687.62, 5686.91, 5686.2, "$", new Date(1508161489827L),0, null));
        list.add(new USD(66, 5697.44, 5697.73, 5697.44, 5697.15, "$", new Date(1508163289669L),0, null));
        list.add(new USD(67, 5689.21, 5690.21, 5689.21, 5688.21, "$", new Date(1508165089847L),0, null));
        list.add(new USD(68, 5698.91, 5699.41, 5698.91, 5698.41, "$", new Date(1508165912949L),0, null));
        list.add(new USD(69, 5697.92, 5698.42, 5697.92, 5697.41, "$", new Date(1508166889917L),0, null));
        list.add(new USD(70, 5673.7, 5673.93, 5673.7, 5673.48, "$", new Date(1508168699083L),0, null));
        list.add(new USD(71, 5706.39, 5706.77, 5706.39, 5706.01, "$", new Date(1508169660931L),0, null));
        list.add(new USD(72, 5688.63, 5689.53, 5688.63, 5687.72, "$", new Date(1508170489817L),0, null));
        list.add(new USD(73, 5655.81, 5656.3, 5655.81, 5655.31, "$", new Date(1508172290209L),0, null));
        list.add(new USD(74, 5673.05, 5673.74, 5673.05, 5672.37, "$", new Date(1508175129882L),0, null));
        list.add(new USD(75, 5673.25, 5674.06, 5673.25, 5672.45, "$", new Date(1508175218297L),0, null));
        list.add(new USD(76, 5695.51, 5696.1, 5695.51, 5694.92, "$", new Date(1508181148878L),0, null));
        list.add(new USD(77, 5696.57, 5697.28, 5696.57, 5695.86, "$", new Date(1508182010454L),0, null));
        list.add(new USD(78, 5703.41, 5704.17, 5703.41, 5702.65, "$", new Date(1508182910450L),0, null));
        list.add(new USD(79, 5702.92, 5703.52, 5702.92, 5702.32, "$", new Date(1508182919927L),0, null));
        list.add(new USD(80, 5704.57, 5704.86, 5704.57, 5704.28, "$", new Date(1508183020351L),0, null));
        list.add(new USD(81, 5702.33, 5703.41, 5702.33, 5701.25, "$", new Date(1508187099451L),0, null));
        list.add(new USD(82, 5694.89, 5695.16, 5694.89, 5694.61, "$", new Date(1508188899007L),0, null));
        list.add(new USD(83, 5715.38, 5716.05, 5715.38, 5714.7, "$", new Date(1508190699034L),0, null));
        list.add(new USD(84, 5711.94, 5713.48, 5711.94, 5710.4, "$", new Date(1508192499218L),0, null));
        list.add(new USD(85, 5708.22, 5708.65, 5708.22, 5707.78, "$", new Date(1508194299032L),0, null));
        list.add(new USD(86, 5783.9, 5784.4, 5783.9, 5783.4, "$", new Date(1508196099025L),0, null));
        list.add(new USD(87, 5757.94, 5758.56, 5757.94, 5757.32, "$", new Date(1508197899015L),0, null));
        list.add(new USD(88, 5766.68, 5767.26, 5766.68, 5766.11, "$", new Date(1508199699017L),0, null));
        list.add(new USD(89, 5755.38, 5755.81, 5755.38, 5754.94, "$", new Date(1508201499038L),0, null));
        list.add(new USD(90, 5751.66, 5752.39, 5751.66, 5750.93, "$", new Date(1508203300038L),0, null));
        list.add(new USD(91, 5647.12, 5647.57, 5647.12, 5646.66, "$", new Date(1508205100048L),0, null));
        list.add(new USD(92, 5644.02, 5644.26, 5644.02, 5643.78, "$", new Date(1508206899050L),0, null));
        list.add(new USD(93, 5634.96, 5636.11, 5634.96, 5633.81, "$", new Date(1508208699051L),0, null));
        list.add(new USD(94, 5641.8, 5642.84, 5641.8, 5640.76, "$", new Date(1508209088352L),0, null));
        list.add(new USD(95, 5632.38, 5633.01, 5632.38, 5631.74, "$", new Date(1508209415745L),0, null));
        list.add(new USD(96, 5639.19, 5639.32, 5639.19, 5639.07, "$", new Date(1508210499002L),0, null));
        list.add(new USD(97, 5627.59, 5627.69, 5627.59, 5627.48, "$", new Date(1508211465911L),0, null));
        list.add(new USD(98, 5644.13, 5644.24, 5644.13, 5644.02, "$", new Date(1508212299990L),0, null));
        list.add(new USD(99, 5652.58, 5653.59, 5652.58, 5651.58, "$", new Date(1508214102320L),0, null));
        list.add(new USD(100, 5651.38, 5652.35, 5651.38, 5650.41, "$", new Date(1508215899458L),0, null));
        list.add(new USD(101, 5624.18, 5624.98, 5624.18, 5623.38, "$", new Date(1508217699253L),0, null));
        list.add(new USD(102, 5627.26, 5628.02, 5627.26, 5626.5, "$", new Date(1508219499171L),0, null));
        list.add(new USD(103, 5641.49, 5641.98, 5641.49, 5640.99, "$", new Date(1508221299242L),0, null));
        list.add(new USD(104, 5671.16, 5672.71, 5671.16, 5669.61, "$", new Date(1508223099158L),0, null));
        list.add(new USD(105, 5663.03, 5664.29, 5663.03, 5661.76, "$", new Date(1508224899207L),0, null));
        list.add(new USD(106, 5655.38, 5655.99, 5655.38, 5654.76, "$", new Date(1508226699238L),0, null));
        list.add(new USD(107, 5660.56, 5661.26, 5660.56, 5659.86, "$", new Date(1508228499245L),0, null));
        list.add(new USD(108, 5674.23, 5674.87, 5674.23, 5673.6, "$", new Date(1508230299047L),0, null));
        list.add(new USD(109, 5675.05, 5675.74, 5675.05, 5674.35, "$", new Date(1508232099056L),0, null));
        list.add(new USD(110, 5670.46, 5671.3, 5670.46, 5669.62, "$", new Date(1508233899287L),0, null));
        list.add(new USD(111, 5671.54, 5672.09, 5671.54, 5670.99, "$", new Date(1508235699430L),0, null));
        list.add(new USD(112, 5672.19, 5673.07, 5672.19, 5671.31, "$", new Date(1508237499339L),0, null));
        list.add(new USD(113, 5637.28, 5637.75, 5637.28, 5636.81, "$", new Date(1508239299074L),0, null));
        list.add(new USD(114, 5556.99, 5557.95, 5556.99, 5556.02, "$", new Date(1508241099043L),0, null));
        list.add(new USD(115, 5523.51, 5524.71, 5523.51, 5522.3, "$", new Date(1508242899045L),0, null));
        list.add(new USD(116, 5553.22, 5554.06, 5553.22, 5552.37, "$", new Date(1508244248023L),0, null));
        list.add(new USD(117, 5565.37, 5566.12, 5565.37, 5564.63, "$", new Date(1508244699084L),0, null));
        list.add(new USD(118, 5567.81, 5568.15, 5567.81, 5567.46, "$", new Date(1508246499170L),0, null));
        list.add(new USD(119, 5587.26, 5587.53, 5587.26, 5586.99, "$", new Date(1508248299091L),0, null));
        list.add(new USD(120, 5607.05, 5607.38, 5607.05, 5606.72, "$", new Date(1508250099078L),0, null));
        list.add(new USD(121, 5606.0, 5606.64, 5606.0, 5605.37, "$", new Date(1508250462638L),0, null));
        list.add(new USD(122, 5595.6, 5596.25, 5595.6, 5594.94, "$", new Date(1508251899410L),0, null));
        list.add(new USD(123, 5570.6, 5571.31, 5570.6, 5569.89, "$", new Date(1508252312600L),0, null));
        list.add(new USD(124, 5568.65, 5569.28, 5568.65, 5568.02, "$", new Date(1508253699019L),0, null));
        list.add(new USD(125, 5575.71, 5576.54, 5575.71, 5574.88, "$", new Date(1508253960345L),0, null));
        list.add(new USD(126, 5587.06, 5587.6, 5587.06, 5586.52, "$", new Date(1508255499045L),0, null));
        list.add(new USD(127, 5585.96, 5586.54, 5585.96, 5585.38, "$", new Date(1508257299111L),0, null));
        list.add(new USD(128, 5579.93, 5580.61, 5579.93, 5579.25, "$", new Date(1508259099058L),0, null));
        list.add(new USD(129, 5580.71, 5582.49, 5580.71, 5578.94, "$", new Date(1508260899089L),0, null));
        list.add(new USD(130, 5628.74, 5629.41, 5628.74, 5628.06, "$", new Date(1508262699148L),0, null));
        list.add(new USD(131, 5627.74, 5628.72, 5627.74, 5626.76, "$", new Date(1508264499229L),0, null));
        list.add(new USD(132, 5630.93, 5631.79, 5630.93, 5630.07, "$", new Date(1508266299044L),0, null));
        list.add(new USD(133, 5615.74, 5615.94, 5615.74, 5615.53, "$", new Date(1508268099007L),0, null));
        list.add(new USD(134, 5625.72, 5626.55, 5625.72, 5624.88, "$", new Date(1508270122120L),0, null));
        list.add(new USD(135, 5628.77, 5630.21, 5628.77, 5627.33, "$", new Date(1508271039817L),0, null));
        list.add(new USD(136, 5593.26, 5594.3, 5593.26, 5592.23, "$", new Date(1508272719815L),0, null));
        list.add(new USD(137, 5582.09, 5583.38, 5582.09, 5580.8, "$", new Date(1508274399826L),0, null));
        list.add(new USD(138, 5586.89, 5587.81, 5586.89, 5585.96, "$", new Date(1508276079840L),0, null));
        list.add(new USD(139, 5573.42, 5574.96, 5573.42, 5571.88, "$", new Date(1508277759841L),0, null));
        list.add(new USD(140, 5590.31, 5592.2, 5590.31, 5588.41, "$", new Date(1508279439809L),0, null));
        list.add(new USD(141, 5569.9, 5570.33, 5569.9, 5569.47, "$", new Date(1508281119836L),0, null));
        list.add(new USD(142, 5570.77, 5571.52, 5570.77, 5570.02, "$", new Date(1508282799813L),0, null));
        list.add(new USD(143, 5595.83, 5596.18, 5595.83, 5595.48, "$", new Date(1508284479806L),0, null));
        list.add(new USD(144, 5525.87, 5527.0, 5525.87, 5524.74, "$", new Date(1508286159880L),0, null));
        list.add(new USD(145, 5521.0, 5521.8, 5521.0, 5520.2, "$", new Date(1508287839865L),0, null));
        list.add(new USD(146, 5508.29, 5508.84, 5508.29, 5507.73, "$", new Date(1508289519872L),0, null));
        list.add(new USD(147, 5506.04, 5506.41, 5506.04, 5505.66, "$", new Date(1508291199810L),0, null));
        list.add(new USD(148, 5474.12, 5474.93, 5474.12, 5473.3, "$", new Date(1508292879827L),0, null));
        list.add(new USD(149, 5502.28, 5503.59, 5502.28, 5500.97, "$", new Date(1508294559895L),0, null));
        list.add(new USD(150, 5480.2, 5480.63, 5480.2, 5479.76, "$", new Date(1508296239778L),0, null));
        list.add(new USD(151, 5466.92, 5467.58, 5466.92, 5466.26, "$", new Date(1508297919818L),0, null));
        list.add(new USD(152, 5471.71, 5472.31, 5471.71, 5471.11, "$", new Date(1508298147120L),0, null));
        list.add(new USD(153, 5513.28, 5514.23, 5513.28, 5512.34, "$", new Date(1508299599943L),0, null));
        list.add(new USD(154, 5513.88, 5514.8, 5513.88, 5512.97, "$", new Date(1508300255635L),0, null));
        list.add(new USD(155, 5388.85, 5389.3, 5388.85, 5388.4, "$", new Date(1508348413122L),0, null));
        list.add(new USD(156, 5363.38, 5363.93, 5363.38, 5362.82, "$", new Date(1508348592545L),0, null));
        list.add(new USD(157, 5373.15, 5373.51, 5373.15, 5372.78, "$", new Date(1508348772412L),0, null));
        list.add(new USD(158, 5375.89, 5376.19, 5375.89, 5375.59, "$", new Date(1508349030470L),0, null));
        list.add(new USD(159, 5370.97, 5371.48, 5370.97, 5370.45, "$", new Date(1508349210035L),0, null));
        list.add(new USD(160, 5374.7, 5375.45, 5374.7, 5373.95, "$", new Date(1508349390056L),0, null));
        list.add(new USD(161, 5376.38, 5377.01, 5376.38, 5375.76, "$", new Date(1508349570060L),0, null));
        list.add(new USD(162, 5375.18, 5376.23, 5375.18, 5374.14, "$", new Date(1508349750092L),0, null));
        list.add(new USD(163, 5366.18, 5366.52, 5366.18, 5365.84, "$", new Date(1508349930016L),0, null));
        list.add(new USD(164, 5370.17, 5370.61, 5370.17, 5369.74, "$", new Date(1508350325201L),0, null));
        list.add(new USD(165, 5392.76, 5393.13, 5392.76, 5392.39, "$", new Date(1508351010540L),0, null));
        list.add(new USD(166, 5425.34, 5425.97, 5425.34, 5424.71, "$", new Date(1508351729008L),0, null));
        list.add(new USD(167, 5427.5, 5427.55, 5427.5, 5427.45, "$", new Date(1508351746777L),0, null));
        list.add(new USD(168, 5412.58, 5413.56, 5412.58, 5411.6, "$", new Date(1508352568470L),0, null));
        list.add(new USD(169, 5403.63, 5404.63, 5403.63, 5402.63, "$", new Date(1508353176438L),0, null));
        list.add(new USD(170, 5407.03, 5407.12, 5407.03, 5406.94, "$", new Date(1508354210509L),0, null));
        list.add(new USD(171, 5389.13, 5389.9, 5389.13, 5388.36, "$", new Date(1508355451297L),0, null));
        list.add(new USD(172, 5392.42, 5393.01, 5392.42, 5391.82, "$", new Date(1508355634310L),0, null));
        list.add(new USD(173, 5408.45, 5408.76, 5408.45, 5408.14, "$", new Date(1508355814396L),0, null));
        list.add(new USD(174, 5408.41, 5408.86, 5408.41, 5407.96, "$", new Date(1508355994329L),0, null));
        list.add(new USD(175, 5396.14, 5396.23, 5396.14, 5396.04, "$", new Date(1508356174340L),0, null));
        list.add(new USD(176, 5399.29, 5399.73, 5399.29, 5398.84, "$", new Date(1508356354338L),0, null));
        list.add(new USD(177, 5397.06, 5397.59, 5397.06, 5396.53, "$", new Date(1508356534327L),0, null));
        list.add(new USD(178, 5400.42, 5400.65, 5400.42, 5400.18, "$", new Date(1508358359654L),0, null));
        list.add(new USD(179, 5493.9, 5494.11, 5493.9, 5493.68, "$", new Date(1508360442297L),0, null));
        list.add(new USD(180, 5493.73, 5494.25, 5493.73, 5493.21, "$", new Date(1508362637491L),0, null));
        list.add(new USD(181, 5507.45, 5508.35, 5507.45, 5506.54, "$", new Date(1508364593259L),0, null));
        list.add(new USD(182, 5567.06, 5567.54, 5567.06, 5566.57, "$", new Date(1508367365914L),0, null));
        list.add(new USD(183, 5595.09, 5596.03, 5595.09, 5594.16, "$", new Date(1508370578367L),0, null));
        list.add(new USD(184, 5519.78, 5520.33, 5519.78, 5519.22, "$", new Date(1508373908121L),0, null));
        list.add(new USD(185, 5585.1, 5586.24, 5585.1, 5583.95, "$", new Date(1508375888209L),0, null));
        list.add(new USD(186, 5636.95, 5637.53, 5636.95, 5636.36, "$", new Date(1508378006824L),0, null));
        list.add(new USD(187, 5627.05, 5627.41, 5627.05, 5626.69, "$", new Date(1508380596562L),0, null));
        list.add(new USD(188, 5606.53, 5607.48, 5606.53, 5605.58, "$", new Date(1508381747742L),0, null));
        list.add(new USD(189, 5606.53, 5607.48, 5606.53, 5605.58, "$", new Date(1508381796474L),0, null));
        list.add(new USD(190, 5604.34, 5604.58, 5604.34, 5604.11, "$", new Date(1508382783038L),0, null));
        list.add(new USD(191, 5609.91, 5611.19, 5609.91, 5608.63, "$", new Date(1508382961750L),0, null));
        list.add(new USD(192, 5607.32, 5607.67, 5607.32, 5606.97, "$", new Date(1508383141723L),0, null));
        list.add(new USD(193, 5600.58, 5602.01, 5600.58, 5599.15, "$", new Date(1508383321719L),0, null));
        list.add(new USD(194, 5600.58, 5602.01, 5600.58, 5599.15, "$", new Date(1508383501734L),0, null));
        list.add(new USD(195, 5614.82, 5615.63, 5614.82, 5614.01, "$", new Date(1508383791044L),0, null));
        list.add(new USD(196, 5609.44, 5610.81, 5609.44, 5608.07, "$", new Date(1508384355939L),0, null));
        list.add(new USD(197, 5615.55, 5616.64, 5615.55, 5614.45, "$", new Date(1508384789903L),0, null));
        list.add(new USD(198, 5617.67, 5619.08, 5617.67, 5616.25, "$", new Date(1508384969729L),0, null));
        list.add(new USD(199, 5643.06, 5643.9, 5643.06, 5642.22, "$", new Date(1508386146517L),0, null));
        list.add(new USD(200, 5650.27, 5650.43, 5650.27, 5650.1, "$", new Date(1508386922211L),0, null));
        list.add(new USD(201, 5651.08, 5652.44, 5651.08, 5649.73, "$", new Date(1508388205602L),0, null));
        list.add(new USD(202, 5642.25, 5642.87, 5642.25, 5641.62, "$", new Date(1508388231006L),0, null));
        list.add(new USD(203, 5642.53, 5645.24, 5642.53, 5639.81, "$", new Date(1508388409187L),0, null));
        list.add(new USD(204, 5631.2, 5632.11, 5631.2, 5630.28, "$", new Date(1508388934617L),0, null));
        list.add(new USD(205, 5631.2, 5632.11, 5631.2, 5630.28, "$", new Date(1508389114371L),0, null));
        list.add(new USD(206, 5632.67, 5633.02, 5632.67, 5632.33, "$", new Date(1508389809631L),0, null));
        list.add(new USD(207, 5611.15, 5611.81, 5611.15, 5610.48, "$", new Date(1508392558853L),0, null));
        list.add(new USD(208, 5605.9, 5606.36, 5605.9, 5605.44, "$", new Date(1508392773516L),0, null));
        list.add(new USD(209, 5587.87, 5588.49, 5587.87, 5587.25, "$", new Date(1508392952827L),0, null));
        list.add(new USD(210, 5601.72, 5601.92, 5601.72, 5601.52, "$", new Date(1508393132836L),0, null));
        list.add(new USD(211, 5615.92, 5616.67, 5615.92, 5615.16, "$", new Date(1508393312882L),0, null));
        list.add(new USD(212, 5612.51, 5612.58, 5612.51, 5612.45, "$", new Date(1508393492934L),0, null));
        list.add(new USD(213, 5615.13, 5615.31, 5615.13, 5614.94, "$", new Date(1508393672930L),0, null));
        list.add(new USD(214, 5610.58, 5611.06, 5610.58, 5610.1, "$", new Date(1508393852747L),0, null));
        list.add(new USD(215, 5608.0, 5608.49, 5608.0, 5607.51, "$", new Date(1508394032755L),0, null));
        list.add(new USD(216, 5600.04, 5600.81, 5600.04, 5599.27, "$", new Date(1508394212788L),0, null));
        list.add(new USD(217, 5598.01, 5598.8, 5598.01, 5597.23, "$", new Date(1508394392742L),0, null));
        list.add(new USD(218, 5598.36, 5598.8, 5598.36, 5597.91, "$", new Date(1508394572777L),0, null));
        list.add(new USD(219, 5598.91, 5599.64, 5598.91, 5598.18, "$", new Date(1508394753008L),0, null));
        list.add(new USD(220, 5607.22, 5608.89, 5607.22, 5605.56, "$", new Date(1508394932782L),0, null));
        list.add(new USD(221, 5599.18, 5599.91, 5599.18, 5598.45, "$", new Date(1508395112777L),0, null));
        list.add(new USD(222, 5597.05, 5597.51, 5597.05, 5596.59, "$", new Date(1508395293021L),0, null));
        list.add(new USD(223, 5597.24, 5597.28, 5597.24, 5597.21, "$", new Date(1508395472775L),0, null));
        list.add(new USD(224, 5584.35, 5585.2, 5584.35, 5583.49, "$", new Date(1508395652974L),0, null));
        list.add(new USD(225, 5567.44, 5567.56, 5567.44, 5567.33, "$", new Date(1508395832709L),0, null));
        list.add(new USD(226, 5563.96, 5564.41, 5563.96, 5563.5, "$", new Date(1508396012725L),0, null));
        list.add(new USD(227, 5568.39, 5568.74, 5568.39, 5568.04, "$", new Date(1508396192723L),0, null));
        list.add(new USD(228, 5569.06, 5569.4, 5569.06, 5568.71, "$", new Date(1508396372857L),0, null));
        list.add(new USD(229, 5566.76, 5567.33, 5566.76, 5566.2, "$", new Date(1508397022072L),0, null));
        list.add(new USD(230, 5558.58, 5558.94, 5558.58, 5558.23, "$", new Date(1508397110985L),0, null));
        list.add(new USD(231, 5558.67, 5559.21, 5558.67, 5558.12, "$", new Date(1508397347559L),0, null));
        list.add(new USD(232, 5583.3, 5583.62, 5583.3, 5582.98, "$", new Date(1508397527434L),0, null));
        list.add(new USD(233, 5585.69, 5587.04, 5585.69, 5584.34, "$", new Date(1508397707390L),0, null));
        list.add(new USD(234, 5586.07, 5586.61, 5586.07, 5585.52, "$", new Date(1508397887403L),0, null));
        list.add(new USD(235, 5584.09, 5585.45, 5584.09, 5582.73, "$", new Date(1508398067427L),0, null));
        list.add(new USD(236, 5586.45, 5587.85, 5586.45, 5585.04, "$", new Date(1508398247419L),0, null));
        list.add(new USD(237, 5586.44, 5587.11, 5586.44, 5585.78, "$", new Date(1508398427392L),0, null));
        list.add(new USD(238, 5601.36, 5602.38, 5601.36, 5600.33, "$", new Date(1508398607391L),0, null));
        list.add(new USD(239, 5598.67, 5599.73, 5598.67, 5597.61, "$", new Date(1508398787406L),0, null));
        list.add(new USD(240, 5602.61, 5603.17, 5602.61, 5602.04, "$", new Date(1508398995156L),0, null));
        list.add(new USD(241, 5604.72, 5606.2, 5604.72, 5603.24, "$", new Date(1508399174583L),0, null));
        list.add(new USD(242, 5598.31, 5599.67, 5598.31, 5596.94, "$", new Date(1508399354764L),0, null));
        list.add(new USD(243, 5599.21, 5600.58, 5599.21, 5597.84, "$", new Date(1508399534589L),0, null));
        list.add(new USD(244, 5597.1, 5598.18, 5597.1, 5596.02, "$", new Date(1508399714518L),0, null));
        list.add(new USD(245, 5596.47, 5597.1, 5596.47, 5595.84, "$", new Date(1508399894870L),0, null));
        list.add(new USD(246, 5602.69, 5603.6, 5602.69, 5601.77, "$", new Date(1508400074531L),0, null));
        list.add(new USD(247, 5589.44, 5590.26, 5589.44, 5588.61, "$", new Date(1508400762513L),0, null));
        list.add(new USD(248, 5636.03, 5636.28, 5636.03, 5635.77, "$", new Date(1508402522811L),0, null));
        list.add(new USD(249, 5630.41, 5632.41, 5630.41, 5628.4, "$", new Date(1508402791672L),0, null));
        list.add(new USD(250, 5623.91, 5624.23, 5623.91, 5623.58, "$", new Date(1508403874270L),0, null));
        list.add(new USD(251, 5607.33, 5609.0, 5607.33, 5605.66, "$", new Date(1508404635929L),0, null));
        list.add(new USD(252, 5615.09, 5615.65, 5615.09, 5614.52, "$", new Date(1508404815464L),0, null));
        list.add(new USD(253, 5622.78, 5623.44, 5622.78, 5622.11, "$", new Date(1508404995673L),0, null));
        list.add(new USD(254, 5627.91, 5628.38, 5627.91, 5627.43, "$", new Date(1508405175463L),0, null));
        list.add(new USD(255, 5636.22, 5636.61, 5636.22, 5635.82, "$", new Date(1508405355418L),0, null));
        list.add(new USD(256, 5643.48, 5643.54, 5643.48, 5643.41, "$", new Date(1508405535460L),0, null));
        list.add(new USD(257, 5645.95, 5646.42, 5645.95, 5645.48, "$", new Date(1508405715483L),0, null));
        list.add(new USD(258, 5639.19, 5639.26, 5639.19, 5639.12, "$", new Date(1508405895499L),0, null));
        list.add(new USD(259, 5637.84, 5638.44, 5637.84, 5637.24, "$", new Date(1508406075471L),0, null));
        list.add(new USD(260, 5661.48, 5661.92, 5661.48, 5661.04, "$", new Date(1508406255482L),0, null));
        list.add(new USD(261, 5691.75, 5691.94, 5691.75, 5691.55, "$", new Date(1508406435468L),0, null));
        list.add(new USD(262, 5693.06, 5693.79, 5693.06, 5692.33, "$", new Date(1508406615496L),0, null));
        list.add(new USD(263, 5680.88, 5682.26, 5680.88, 5679.49, "$", new Date(1508406795894L),0, null));
        list.add(new USD(264, 5690.01, 5691.28, 5690.01, 5688.74, "$", new Date(1508406975365L),0, null));
        list.add(new USD(265, 5691.58, 5693.19, 5691.58, 5689.97, "$", new Date(1508407155283L),0, null));
        list.add(new USD(266, 5691.45, 5692.67, 5691.45, 5690.23, "$", new Date(1508407335288L),0, null));
        list.add(new USD(267, 5691.78, 5691.91, 5691.78, 5691.64, "$", new Date(1508407515362L),0, null));
        list.add(new USD(268, 5690.7, 5691.07, 5690.7, 5690.33, "$", new Date(1508407695305L),0, null));
        list.add(new USD(269, 5670.9, 5671.28, 5670.9, 5670.52, "$", new Date(1508407875277L),0, null));
        list.add(new USD(270, 5672.99, 5673.91, 5672.99, 5672.07, "$", new Date(1508408055291L),0, null));
        list.add(new USD(271, 5669.03, 5669.44, 5669.03, 5668.61, "$", new Date(1508408235335L),0, null));
        list.add(new USD(272, 5671.23, 5671.43, 5671.23, 5671.02, "$", new Date(1508408415280L),0, null));
        list.add(new USD(273, 5671.34, 5672.34, 5671.34, 5670.34, "$", new Date(1508408595535L),0, null));
        list.add(new USD(274, 5674.82, 5675.63, 5674.82, 5674.02, "$", new Date(1508408775280L),0, null));
        list.add(new USD(275, 5681.46, 5682.18, 5681.46, 5680.73, "$", new Date(1508408955293L),0, null));
        list.add(new USD(276, 5691.6, 5693.12, 5691.6, 5690.08, "$", new Date(1508409135283L),0, null));
        list.add(new USD(277, 5711.64, 5712.22, 5711.64, 5711.05, "$", new Date(1508409315298L),0, null));
        list.add(new USD(278, 5711.63, 5712.37, 5711.63, 5710.89, "$", new Date(1508409495291L),0, null));
        list.add(new USD(279, 5709.11, 5709.61, 5709.11, 5708.61, "$", new Date(1508409675350L),0, null));
        list.add(new USD(280, 5697.94, 5699.18, 5697.94, 5696.7, "$", new Date(1508409855288L),0, null));
        list.add(new USD(281, 5697.05, 5697.94, 5697.05, 5696.16, "$", new Date(1508410035308L),0, null));
        list.add(new USD(282, 5684.88, 5686.0, 5684.88, 5683.76, "$", new Date(1508410215295L),0, null));
        list.add(new USD(283, 5685.34, 5686.5, 5685.34, 5684.19, "$", new Date(1508410395278L),0, null));
        list.add(new USD(284, 5689.67, 5690.41, 5689.67, 5688.92, "$", new Date(1508410575356L),0, null));
        list.add(new USD(285, 5685.53, 5686.1, 5685.53, 5684.95, "$", new Date(1508410755279L),0, null));
        list.add(new USD(286, 5690.89, 5691.71, 5690.89, 5690.06, "$", new Date(1508411180156L),0, null));
        list.add(new USD(287, 5698.27, 5699.21, 5698.27, 5697.33, "$", new Date(1508412143538L),0, null));
        list.add(new USD(288, 5686.75, 5688.37, 5686.75, 5685.12, "$", new Date(1508414854965L),0, null));
        list.add(new USD(289, 5657.42, 5658.5, 5657.42, 5656.35, "$", new Date(1508415689521L),0, null));
        list.add(new USD(290, 5652.54, 5653.73, 5652.54, 5651.35, "$", new Date(1508415868539L),0, null));
        list.add(new USD(291, 5663.68, 5664.4, 5663.68, 5662.97, "$", new Date(1508416048509L),0, null));
        list.add(new USD(292, 5663.68, 5664.4, 5663.68, 5662.97, "$", new Date(1508416228956L),0, null));
        list.add(new USD(293, 5669.09, 5669.35, 5669.09, 5668.82, "$", new Date(1508416408670L),0, null));
        list.add(new USD(294, 5674.66, 5674.72, 5674.66, 5674.6, "$", new Date(1508416588943L),0, null));
        list.add(new USD(295, 5673.89, 5674.19, 5673.89, 5673.6, "$", new Date(1508416768531L),0, null));
        list.add(new USD(296, 5664.98, 5665.66, 5664.98, 5664.3, "$", new Date(1508416948514L),0, null));
        list.add(new USD(297, 5673.8, 5674.41, 5673.8, 5673.19, "$", new Date(1508417129047L),0, null));
        list.add(new USD(298, 5671.68, 5672.14, 5671.68, 5671.23, "$", new Date(1508417308791L),0, null));
        list.add(new USD(299, 5672.62, 5673.12, 5672.62, 5672.11, "$", new Date(1508417488761L),0, null));
        list.add(new USD(300, 5672.64, 5673.18, 5672.64, 5672.1, "$", new Date(1508417668590L),0, null));
        list.add(new USD(301, 5663.09, 5663.73, 5663.09, 5662.45, "$", new Date(1508417848598L),0, null));
        list.add(new USD(302, 5659.71, 5660.55, 5659.71, 5658.86, "$", new Date(1508418028987L),0, null));
        list.add(new USD(303, 5696.49, 5696.73, 5696.49, 5696.25, "$", new Date(1508419528826L),0, null));
        list.add(new USD(304, 5699.29, 5699.71, 5699.29, 5698.86, "$", new Date(1508419708662L),0, null));
        list.add(new USD(305, 5683.19, 5683.94, 5683.19, 5682.44, "$", new Date(1508419888661L),0, null));
        list.add(new USD(306, 5695.11, 5695.67, 5695.11, 5694.54, "$", new Date(1508420069063L),0, null));
        list.add(new USD(307, 5691.81, 5692.42, 5691.81, 5691.19, "$", new Date(1508420248838L),0, null));
        list.add(new USD(308, 5714.98, 5715.4, 5714.98, 5714.55, "$", new Date(1508420428644L),0, null));
        list.add(new USD(309, 5715.43, 5715.59, 5715.43, 5715.26, "$", new Date(1508420608893L),0, null));
        list.add(new USD(310, 5715.93, 5716.04, 5715.93, 5715.83, "$", new Date(1508420788796L),0, null));
        list.add(new USD(311, 5710.82, 5711.08, 5710.82, 5710.55, "$", new Date(1508420968771L),0, null));
        list.add(new USD(312, 5723.15, 5723.45, 5723.15, 5722.85, "$", new Date(1508421148632L),0, null));
        list.add(new USD(313, 5725.13, 5725.69, 5725.13, 5724.57, "$", new Date(1508421328897L),0, null));
        list.add(new USD(314, 5735.87, 5736.51, 5735.87, 5735.22, "$", new Date(1508421508650L),0, null));
        list.add(new USD(315, 5736.72, 5737.81, 5736.72, 5735.62, "$", new Date(1508421689105L),0, null));
        list.add(new USD(316, 5730.59, 5732.13, 5730.59, 5729.05, "$", new Date(1508421968556L),0, null));
        list.add(new USD(317, 5723.4, 5724.75, 5723.4, 5722.06, "$", new Date(1508424176913L),0, null));
        list.add(new USD(318, 5722.97, 5723.94, 5722.97, 5721.99, "$", new Date(1508424667451L),0, null));
        list.add(new USD(319, 5706.58, 5707.77, 5706.58, 5705.39, "$", new Date(1508425333373L),0, null));
        list.add(new USD(320, 5721.93, 5724.74, 5721.93, 5719.12, "$", new Date(1508425513420L),0, null));
        list.add(new USD(321, 5720.67, 5721.21, 5720.67, 5720.13, "$", new Date(1508425693441L),0, null));
        list.add(new USD(322, 5690.53, 5691.62, 5690.53, 5689.44, "$", new Date(1508426448440L),0, null));
        list.add(new USD(323, 5639.94, 5640.77, 5639.94, 5639.12, "$", new Date(1508429201294L),0, null));
        list.add(new USD(324, 5661.49, 5662.69, 5661.49, 5660.28, "$", new Date(1508431098806L),0, null));
        list.add(new USD(325, 5667.22, 5668.27, 5667.22, 5666.16, "$", new Date(1508432628806L),0, null));
        list.add(new USD(326, 5674.08, 5674.88, 5674.08, 5673.28, "$", new Date(1508432807284L),0, null));
        list.add(new USD(327, 6152.22, 6152.91, 6152.22, 6151.52, "$", new Date(1508601811188L),-1, null));
        list.add(new USD(328, 6150.57, 6151.63, 6150.57, 6149.51, "$", new Date(1508601811251L),1, 500.0));
        list.add(new USD(329, 6150.29, 6151.19, 6150.29, 6149.39, "$", new Date(1508602055675L),0, null));
        list.add(new USD(330, 6150.97, 6151.9, 6150.97, 6150.04, "$", new Date(1508602314935L),1, 500.0));
        list.add(new USD(331, 6149.07, 6149.8, 6149.07, 6148.34, "$", new Date(1508602644083L),0, null));
        list.add(new USD(332, 6158.83, 6159.5, 6158.83, 6158.16, "$", new Date(1508602885438L),0, null));
        list.add(new USD(333, 6158.37, 6158.81, 6158.37, 6157.93, "$", new Date(1508603143258L),0, null));
        list.add(new USD(334, 6157.91, 6158.34, 6157.91, 6157.48, "$", new Date(1508603508651L),0, null));
        list.add(new USD(335, 6155.94, 6156.58, 6155.94, 6155.3, "$", new Date(1508603778464L),0, null));
        list.add(new USD(336, 6158.28, 6158.65, 6158.28, 6157.9, "$", new Date(1508604037480L),-1, 250.24382711032365));
        list.add(new USD(337, 6132.82, 6133.79, 6132.82, 6131.86, "$", new Date(1508604346849L),0, null));
        list.add(new USD(338, 6130.98, 6131.7, 6130.98, 6130.26, "$", new Date(1508604607232L),0, null));
        list.add(new USD(339, 6121.72, 6123.3, 6121.72, 6120.14, "$", new Date(1508605077770L),0, null));
        list.add(new USD(340, 6122.16, 6123.51, 6122.16, 6120.8, "$", new Date(1508605340430L),-1, 124.36808140574459));
        list.add(new USD(341, 6088.42, 6089.08, 6088.42, 6087.76, "$", new Date(1508605637180L),0, null));
        list.add(new USD(342, 6013.06, 6014.07, 6013.06, 6012.05, "$", new Date(1508605877516L),0, null));
        list.add(new USD(343, 6028.39, 6030.69, 6028.39, 6026.08, "$", new Date(1508606223909L),-1, 61.22173637412833));
        list.add(new USD(344, 5998.64, 5999.2, 5998.64, 5998.08, "$", new Date(1508606538696L),0, null));
        list.add(new USD(345, 5980.18, 5981.5, 5980.18, 5978.85, "$", new Date(1508606846016L),0, null));
        list.add(new USD(346, 5966.61, 5967.91, 5966.61, 5965.3, "$", new Date(1508607286118L),0, null));
        list.add(new USD(347, 5936.14, 5937.46, 5936.14, 5934.82, "$", new Date(1508607603515L),1, 935.8336448901966));
        list.add(new USD(348, 5996.27, 5998.26, 5996.27, 5994.29, "$", new Date(1508607862623L),-1, 502.8448404387713));
        list.add(new USD(349, 6021.24, 6022.83, 6021.24, 6019.64, "$", new Date(1508608104703L),0, null));
        list.add(new USD(350, 6024.66, 6026.0, 6024.66, 6023.32, "$", new Date(1508608350899L),0, null));
        list.add(new USD(351, 5986.71, 5989.05, 5986.71, 5984.36, "$", new Date(1508608838548L),1, 251.42242021938566));
        list.add(new USD(352, 6010.09, 6010.49, 6010.09, 6009.7, "$", new Date(1508609172105L),0, null));
        list.add(new USD(353, 5965.81, 5967.75, 5965.81, 5963.87, "$", new Date(1508609421797L),0, null));
        list.add(new USD(354, 5983.4, 5984.53, 5983.4, 5982.28, "$", new Date(1508609662989L),0, null));
        list.add(new USD(355, 5992.7, 5992.86, 5992.7, 5992.54, "$", new Date(1508609925947L),0, null));
        list.add(new USD(356, 6004.22, 6004.57, 6004.22, 6003.87, "$", new Date(1508610198752L),0, null));
        list.add(new USD(357, 5998.98, 6000.14, 5998.98, 5997.82, "$", new Date(1508610480487L),1, 125.71121010969283));
        list.add(new USD(358, 6002.79, 6003.13, 6002.79, 6002.45, "$", new Date(1508610782613L),0, null));
        list.add(new USD(359, 6038.39, 6041.53, 6038.39, 6035.25, "$", new Date(1508611096119L),0, null));
        list.add(new USD(360, 6031.55, 6032.65, 6031.55, 6030.45, "$", new Date(1508611481212L),1, 62.855605054846414));
        list.add(new USD(361, 6027.7, 6028.75, 6027.7, 6026.64, "$", new Date(1508611724660L),0, null));
        list.add(new USD(362, 6034.37, 6034.92, 6034.37, 6033.81, "$", new Date(1508611981427L),-1, 474.3729309635621));
        list.add(new USD(363, 6041.44, 6043.23, 6041.44, 6039.65, "$", new Date(1508612314378L),0, null));
        list.add(new USD(364, 6049.75, 6050.2, 6049.75, 6049.29, "$", new Date(1508612660290L),0, null));
        list.add(new USD(365, 6039.24, 6039.86, 6039.24, 6038.62, "$", new Date(1508613023979L),1, 268.61426800920424));
        list.add(new USD(366, 6027.09, 6027.65, 6027.09, 6026.52, "$", new Date(1508613386257L),0, null));
        list.add(new USD(367, 6034.88, 6035.28, 6034.88, 6034.48, "$", new Date(1508613669498L),-1, 371.400302935182));
        list.add(new USD(368, 6038.65, 6039.66, 6038.65, 6037.63, "$", new Date(1508613976407L),0, null));
        list.add(new USD(369, 6034.05, 6034.41, 6034.05, 6033.69, "$", new Date(1508614382506L),1, 320.00728547219313));
        list.add(new USD(370, 6047.18, 6048.01, 6047.18, 6046.34, "$", new Date(1508614744354L),0, null));
        list.add(new USD(371, 6043.01, 6043.61, 6043.01, 6042.4, "$", new Date(1508615068916L),0, null));
        list.add(new USD(372, 6047.05, 6047.58, 6047.05, 6046.51, "$", new Date(1508615355328L),0, null));
        list.add(new USD(373, 6049.89, 6050.22, 6049.89, 6049.56, "$", new Date(1508615611716L),0, null));
        list.add(new USD(374, 6035.3, 6035.77, 6035.3, 6034.83, "$", new Date(1508615852256L),1, 160.00364273609657));
        list.add(new USD(375, 6027.47, 6027.99, 6027.47, 6026.94, "$", new Date(1508616139286L),0, null));
        list.add(new USD(376, 6044.39, 6045.42, 6044.39, 6043.35, "$", new Date(1508616443210L),-1, 426.31608966436147));
        list.add(new USD(377, 6056.9, 6057.07, 6056.9, 6056.72, "$", new Date(1508616817828L),0, null));
        list.add(new USD(378, 6091.65, 6092.04, 6091.65, 6091.25, "$", new Date(1508617095515L),0, null));
        list.add(new USD(379, 6102.49, 6102.89, 6102.49, 6102.08, "$", new Date(1508617422986L),-1, 430.4590805462379));
        list.add(new USD(380, 6102.88, 6103.03, 6102.88, 6102.72, "$", new Date(1508617698334L),-1, null));
        list.add(new USD(381, 6085.48, 6086.19, 6085.48, 6084.77, "$", new Date(1508618046081L),1, 508.389406473348));
        list.add(new USD(382, 6087.62, 6088.7, 6087.62, 6086.53, "$", new Date(1508618360159L),0, null));
        list.add(new USD(383, 6091.92, 6092.65, 6091.92, 6091.18, "$", new Date(1508618720490L),0, null));
        list.add(new USD(384, 6096.29, 6097.28, 6096.29, 6095.3, "$", new Date(1508619088502L),0, null));
        list.add(new USD(385, 6093.68, 6093.92, 6093.68, 6093.44, "$", new Date(1508619394274L),1, 254.194703236674));
        list.add(new USD(386, 6086.55, 6087.47, 6086.55, 6085.62, "$", new Date(1508619708403L),0, null));
        list.add(new USD(387, 6094.15, 6095.06, 6094.15, 6093.23, "$", new Date(1508619971658L),-1, 381.5716952835967));
        list.add(new USD(388, 6108.78, 6108.84, 6108.78, 6108.71, "$", new Date(1508620348249L),0, null));
        list.add(new USD(389, 6110.39, 6111.03, 6110.39, 6109.75, "$", new Date(1508620647671L),0, null));
        list.add(new USD(390, 6098.79, 6099.3, 6098.79, 6098.27, "$", new Date(1508620951361L),1, 317.88319926013537));
        list.add(new USD(391, 6100.42, 6101.34, 6100.42, 6099.49, "$", new Date(1508621549327L),0, null));
        list.add(new USD(392, 6097.09, 6098.12, 6097.09, 6096.06, "$", new Date(1508621800068L),0, null));
        list.add(new USD(393, 6086.88, 6087.75, 6086.88, 6086.02, "$", new Date(1508622089248L),0, null));
        list.add(new USD(394, 6080.12, 6081.22, 6080.12, 6079.02, "$", new Date(1508622373373L),0, null));
        list.add(new USD(395, 6073.96, 6075.61, 6073.96, 6072.3, "$", new Date(1508622629140L),1, 317.88319926013537));
        list.add(new USD(396, 5940.87, 5941.47, 5940.87, 5940.27, "$", new Date(1508641200706L),1, null));
        list.add(new USD(397, 5942.45, 5944.74, 5942.45, 5940.16, "$", new Date(1508641575152L),-1, 496.18577305200427));
        list.add(new USD(398, 5938.98, 5939.67, 5938.98, 5938.29, "$", new Date(1508641820166L),0, null));
        list.add(new USD(399, 5937.89, 5938.32, 5937.89, 5937.45, "$", new Date(1508642220316L),0, null));
        list.add(new USD(400, 5973.32, 5974.36, 5973.32, 5972.27, "$", new Date(1508642620348L),-1, 249.43397205002));
        list.add(new USD(401, 5939.08, 5939.73, 5939.08, 5938.44, "$", new Date(1508643021324L),0, null));
        list.add(new USD(402, 5952.27, 5952.77, 5952.27, 5951.77, "$", new Date(1508643382196L),0, null));
        list.add(new USD(403, 5948.41, 5948.68, 5948.41, 5948.14, "$", new Date(1508643760133L),0, null));
        list.add(new USD(404, 5947.82, 5948.17, 5947.82, 5947.46, "$", new Date(1508644133424L),0, null));
        list.add(new USD(405, 5947.42, 5947.85, 5947.42, 5946.99, "$", new Date(1508644533378L),0, null));
        list.add(new USD(406, 5945.5, 5946.07, 5945.5, 5944.94, "$", new Date(1508644883174L),1, 745.6197451020242));
        list.add(new USD(407, 5911.2, 5911.76, 5911.2, 5910.63, "$", new Date(1508645277264L),1, null));
        list.add(new USD(408, 5852.79, 5854.79, 5852.79, 5850.78, "$", new Date(1508645655136L),1, null));
        list.add(new USD(409, 5843.05, 5843.84, 5843.05, 5842.26, "$", new Date(1508646006929L),1, null));
        list.add(new USD(410, 5834.07, 5836.37, 5834.07, 5831.76, "$", new Date(1508646303238L),1, null));
        list.add(new USD(411, 5865.35, 5866.22, 5865.35, 5864.48, "$", new Date(1508646698424L),-1, 490.1603414145337));
        list.add(new USD(412, 5864.63, 5866.52, 5864.63, 5862.73, "$", new Date(1508647099548L),0, null));
        list.add(new USD(413, 5857.71, 5858.34, 5857.71, 5857.08, "$", new Date(1508647363976L),0, null));
        list.add(new USD(414, 5841.53, 5842.1, 5841.53, 5840.95, "$", new Date(1508647853270L),0, null));
        list.add(new USD(415, 5803.53, 5806.23, 5803.53, 5800.84, "$", new Date(1508648133090L),1, 490.1603414145337));
        list.add(new USD(416, 5819.24, 5819.78, 5819.24, 5818.7, "$", new Date(1508648508086L),-1, 488.77352447735166));
        list.add(new USD(417, 5785.97, 5788.34, 5785.97, 5783.61, "$", new Date(1508648837014L),0, null));
        list.add(new USD(418, 5814.06, 5814.81, 5814.06, 5813.31, "$", new Date(1508649132397L),0, null));
        list.add(new USD(419, 5824.49, 5825.14, 5824.49, 5823.83, "$", new Date(1508649533254L),0, null));
        list.add(new USD(420, 5841.62, 5842.83, 5841.62, 5840.41, "$", new Date(1508649819432L),0, null));
        list.add(new USD(421, 5858.73, 5859.26, 5858.73, 5858.2, "$", new Date(1508650219311L),-1, 492.0915429723515));
        list.add(new USD(422, 5881.6, 5882.19, 5881.6, 5881.01, "$", new Date(1508650620296L),-1, null));
        list.add(new USD(423, 5860.65, 5862.01, 5860.65, 5859.28, "$", new Date(1508651020325L),1, 490.43253372485157));
        list.add(new USD(424, 5885.91, 5888.19, 5885.91, 5883.63, "$", new Date(1508651323878L),0, null));
        list.add(new USD(425, 5885.34, 5886.61, 5885.34, 5884.08, "$", new Date(1508651633390L),0, null));
        list.add(new USD(426, 5862.98, 5863.41, 5862.98, 5862.54, "$", new Date(1508651979008L),0, null));
        list.add(new USD(427, 5859.22, 5859.89, 5859.22, 5858.54, "$", new Date(1508652223431L),0, null));
        list.add(new USD(428, 5822.57, 5823.13, 5822.57, 5822.01, "$", new Date(1508652623312L),1, 490.43253372485157));
        list.add(new USD(429, 5806.74, 5807.64, 5806.74, 5805.83, "$", new Date(1508653007299L),1, null));
        list.add(new USD(430, 5810.3, 5811.35, 5810.3, 5809.25, "$", new Date(1508653393293L),-1, 487.64101046482193));
        list.add(new USD(431, 5843.12, 5844.33, 5843.12, 5841.92, "$", new Date(1508653796744L),0, null));
        list.add(new USD(432, 5846.14, 5846.41, 5846.14, 5845.88, "$", new Date(1508654040547L),0, null));
        list.add(new USD(433, 5843.47, 5844.09, 5843.47, 5842.85, "$", new Date(1508654418679L),1, 243.82050523241097));
        list.add(new USD(434, 5830.37, 5830.96, 5830.37, 5829.77, "$", new Date(1508654797903L),0, null));
        list.add(new USD(435, 5846.02, 5846.63, 5846.02, 5845.41, "$", new Date(1508655071103L),-1, 367.27596792846134));
        list.add(new USD(436, 5860.57, 5862.09, 5860.57, 5859.05, "$", new Date(1508655432148L),0, null));
        list.add(new USD(437, 5881.4, 5881.84, 5881.4, 5880.97, "$", new Date(1508655717574L),0, null));
        return list;
    }
}
