package ru.savchenko.andrey.blockchain.repositories;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.Sort;
import ru.savchenko.andrey.blockchain.base.BaseAdapter;
import ru.savchenko.andrey.blockchain.base.BaseRepository;
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

    public List<USD>getUSDListByDate(Date start, Date end){
        List<USD>usds = realmInstance().where(USD.class)
                .between("date", start, end)
                .findAll();
        realmInstance().close();
        return usds;
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
        list.add(new USD(1 , 5524.71 , 5525.11 , 5524.71 , 5524.3 , "$" , new Date(1508079095375L)));
        list.add(new USD(2 , 5551.9 , 5553.15 , 5551.9 , 5550.65 , "$" , new Date(1508080971617L)));
        list.add(new USD(3 , 5553.38 , 5554.64 , 5553.38 , 5552.11 , "$" , new Date(1508081119786L)));
        list.add(new USD(4 , 5553.38 , 5554.64 , 5553.38 , 5552.11 , "$" , new Date(1508081129600L)));
        list.add(new USD(5 , 5558.91 , 5559.71 , 5558.91 , 5558.1 , "$" , new Date(1508081492552L)));
        list.add(new USD(6 , 5544.01 , 5544.4 , 5544.01 , 5543.62 , "$" , new Date(1508082392368L)));
        list.add(new USD(7 , 5532.5 , 5533.35 , 5532.5 , 5531.65 , "$" , new Date(1508083292496L)));
        list.add(new USD(8 , 5538.89 , 5540.24 , 5538.89 , 5537.54 , "$" , new Date(1508084192411L)));
        list.add(new USD(9 , 5531.39 , 5531.8 , 5531.39 , 5530.98 , "$" , new Date(1508085092642L)));
        list.add(new USD(10 , 5501.19 , 5501.66 , 5501.19 , 5500.72 , "$" , new Date(1508085992417L)));
        list.add(new USD(11 , 5496.33 , 5496.62 , 5496.33 , 5496.04 , "$" , new Date(1508086892367L)));
        list.add(new USD(12 , 5510.71 , 5510.86 , 5510.71 , 5510.55 , "$" , new Date(1508087792431L)));
        list.add(new USD(13 , 5507.99 , 5508.37 , 5507.99 , 5507.62 , "$" , new Date(1508088352698L)));
        list.add(new USD(14 , 5510.4 , 5510.92 , 5510.4 , 5509.87 , "$" , new Date(1508088692682L)));
        list.add(new USD(15 , 5513.54 , 5515.17 , 5513.54 , 5511.91 , "$" , new Date(1508089592418L)));
        list.add(new USD(16 , 5524.56 , 5525.16 , 5524.56 , 5523.95 , "$" , new Date(1508091442329L)));
        list.add(new USD(17 , 5507.36 , 5508.18 , 5507.36 , 5506.53 , "$" , new Date(1508093241987L)));
        list.add(new USD(18 , 5529.14 , 5529.86 , 5529.14 , 5528.41 , "$" , new Date(1508095041820L)));
        list.add(new USD(19 , 5685.93 , 5686.26 , 5685.93 , 5685.59 , "$" , new Date(1508120091340L)));
        list.add(new USD(20 , 5705.98 , 5707.18 , 5705.98 , 5704.78 , "$" , new Date(1508121889533L)));
        list.add(new USD(21 , 5688.68 , 5690.15 , 5688.68 , 5687.21 , "$" , new Date(1508123238513L)));
        list.add(new USD(22 , 5692.04 , 5692.96 , 5692.04 , 5691.11 , "$" , new Date(1508123689536L)));
        list.add(new USD(23 , 5632.58 , 5633.78 , 5632.58 , 5631.37 , "$" , new Date(1508125489566L)));
        list.add(new USD(24 , 5621.42 , 5621.99 , 5621.42 , 5620.85 , "$" , new Date(1508126164630L)));
        list.add(new USD(25 , 5622.0 , 5622.9 , 5622.0 , 5621.09 , "$" , new Date(1508127289609L)));
        list.add(new USD(26 , 5630.26 , 5630.86 , 5630.26 , 5629.67 , "$" , new Date(1508129089691L)));
        list.add(new USD(27 , 5603.68 , 5605.22 , 5603.68 , 5602.14 , "$" , new Date(1508130889625L)));
        list.add(new USD(28 , 5678.5 , 5679.98 , 5678.5 , 5677.02 , "$" , new Date(1508132689766L)));
        list.add(new USD(29 , 5656.97 , 5658.26 , 5656.97 , 5655.67 , "$" , new Date(1508134041045L)));
        list.add(new USD(30 , 5645.73 , 5646.32 , 5645.73 , 5645.13 , "$" , new Date(1508134422419L)));
        list.add(new USD(31 , 5643.01 , 5643.5 , 5643.01 , 5642.52 , "$" , new Date(1508134489403L)));
        list.add(new USD(32 , 5623.84 , 5624.95 , 5623.84 , 5622.73 , "$" , new Date(1508135385787L)));
        list.add(new USD(33 , 5630.01 , 5630.92 , 5630.01 , 5629.1 , "$" , new Date(1508136289557L)));
        list.add(new USD(34 , 5648.21 , 5650.2 , 5648.21 , 5646.21 , "$" , new Date(1508137962348L)));
        list.add(new USD(35 , 5647.53 , 5649.22 , 5647.53 , 5645.83 , "$" , new Date(1508138089591L)));
        list.add(new USD(36 , 5610.03 , 5611.75 , 5610.03 , 5608.32 , "$" , new Date(1508139889598L)));
        list.add(new USD(37 , 5630.88 , 5631.26 , 5630.88 , 5630.5 , "$" , new Date(1508141025095L)));
        list.add(new USD(38 , 5625.78 , 5626.85 , 5625.78 , 5624.7 , "$" , new Date(1508141276892L)));
        list.add(new USD(39 , 5617.81 , 5618.29 , 5617.81 , 5617.32 , "$" , new Date(1508141689657L)));
        list.add(new USD(40 , 5611.54 , 5612.21 , 5611.54 , 5610.86 , "$" , new Date(1508143494683L)));
        list.add(new USD(41 , 5615.71 , 5617.06 , 5615.71 , 5614.36 , "$" , new Date(1508143636257L)));
        list.add(new USD(42 , 5599.27 , 5599.71 , 5599.27 , 5598.82 , "$" , new Date(1508145289899L)));
        list.add(new USD(43 , 5587.47 , 5588.32 , 5587.47 , 5586.62 , "$" , new Date(1508145699338L)));
        list.add(new USD(44 , 5583.15 , 5583.33 , 5583.15 , 5582.97 , "$" , new Date(1508145711763L)));
        list.add(new USD(45 , 5583.15 , 5583.33 , 5583.15 , 5582.97 , "$" , new Date(1508145762094L)));
        list.add(new USD(46 , 5613.46 , 5614.6 , 5613.46 , 5612.31 , "$" , new Date(1508147089776L)));
        list.add(new USD(47 , 5650.58 , 5651.55 , 5650.58 , 5649.61 , "$" , new Date(1508147886344L)));
        list.add(new USD(48 , 5635.45 , 5635.68 , 5635.45 , 5635.21 , "$" , new Date(1508148704608L)));
        list.add(new USD(49 , 5641.23 , 5643.92 , 5641.23 , 5638.55 , "$" , new Date(1508148889597L)));
        list.add(new USD(50 , 5709.92 , 5711.11 , 5709.92 , 5708.74 , "$" , new Date(1508150689950L)));
        list.add(new USD(51 , 5707.51 , 5708.0 , 5707.51 , 5707.03 , "$" , new Date(1508151917225L)));
        list.add(new USD(52 , 5714.46 , 5714.49 , 5714.46 , 5714.42 , "$" , new Date(1508152489528L)));
        list.add(new USD(53 , 5722.56 , 5723.2 , 5722.56 , 5721.91 , "$" , new Date(1508154289813L)));
        list.add(new USD(54 , 5748.66 , 5749.2 , 5748.66 , 5748.11 , "$" , new Date(1508155364991L)));
        list.add(new USD(55 , 5738.66 , 5739.01 , 5738.66 , 5738.3 , "$" , new Date(1508156089795L)));
        list.add(new USD(56 , 5738.52 , 5739.13 , 5738.52 , 5737.92 , "$" , new Date(1508157889760L)));
        list.add(new USD(57 , 5720.79 , 5721.21 , 5720.79 , 5720.36 , "$" , new Date(1508158929986L)));
        list.add(new USD(58 , 5710.56 , 5710.62 , 5710.56 , 5710.5 , "$" , new Date(1508159456239L)));
        list.add(new USD(59 , 5710.56 , 5710.62 , 5710.56 , 5710.5 , "$" , new Date(1508159469681L)));
        list.add(new USD(60 , 5707.04 , 5708.06 , 5707.04 , 5706.01 , "$" , new Date(1508159551336L)));
        list.add(new USD(61 , 5713.37 , 5713.73 , 5713.37 , 5713.0 , "$" , new Date(1508159689613L)));
        list.add(new USD(62 , 5712.6 , 5713.13 , 5712.6 , 5712.07 , "$" , new Date(1508159711994L)));
        list.add(new USD(63 , 5713.37 , 5713.73 , 5713.37 , 5713.0 , "$" , new Date(1508159870297L)));
        list.add(new USD(64 , 5684.36 , 5684.71 , 5684.36 , 5684.01 , "$" , new Date(1508160925249L)));
        list.add(new USD(65 , 5686.91 , 5687.62 , 5686.91 , 5686.2 , "$" , new Date(1508161489827L)));
        list.add(new USD(66 , 5697.44 , 5697.73 , 5697.44 , 5697.15 , "$" , new Date(1508163289669L)));
        list.add(new USD(67 , 5689.21 , 5690.21 , 5689.21 , 5688.21 , "$" , new Date(1508165089847L)));
        list.add(new USD(68 , 5698.91 , 5699.41 , 5698.91 , 5698.41 , "$" , new Date(1508165912949L)));
        list.add(new USD(69 , 5697.92 , 5698.42 , 5697.92 , 5697.41 , "$" , new Date(1508166889917L)));
        list.add(new USD(70 , 5673.7 , 5673.93 , 5673.7 , 5673.48 , "$" , new Date(1508168699083L)));
        list.add(new USD(71 , 5706.39 , 5706.77 , 5706.39 , 5706.01 , "$" , new Date(1508169660931L)));
        list.add(new USD(72 , 5688.63 , 5689.53 , 5688.63 , 5687.72 , "$" , new Date(1508170489817L)));
        list.add(new USD(73 , 5655.81 , 5656.3 , 5655.81 , 5655.31 , "$" , new Date(1508172290209L)));
        list.add(new USD(74 , 5673.05 , 5673.74 , 5673.05 , 5672.37 , "$" , new Date(1508175129882L)));
        list.add(new USD(75 , 5673.25 , 5674.06 , 5673.25 , 5672.45 , "$" , new Date(1508175218297L)));
        list.add(new USD(76 , 5695.51 , 5696.1 , 5695.51 , 5694.92 , "$" , new Date(1508181148878L)));
        list.add(new USD(77 , 5696.57 , 5697.28 , 5696.57 , 5695.86 , "$" , new Date(1508182010454L)));
        list.add(new USD(78 , 5703.41 , 5704.17 , 5703.41 , 5702.65 , "$" , new Date(1508182910450L)));
        list.add(new USD(79 , 5702.92 , 5703.52 , 5702.92 , 5702.32 , "$" , new Date(1508182919927L)));
        list.add(new USD(80 , 5704.57 , 5704.86 , 5704.57 , 5704.28 , "$" , new Date(1508183020351L)));
        list.add(new USD(81 , 5702.33 , 5703.41 , 5702.33 , 5701.25 , "$" , new Date(1508187099451L)));
        list.add(new USD(82 , 5694.89 , 5695.16 , 5694.89 , 5694.61 , "$" , new Date(1508188899007L)));
        list.add(new USD(83 , 5715.38 , 5716.05 , 5715.38 , 5714.7 , "$" , new Date(1508190699034L)));
        list.add(new USD(84 , 5711.94 , 5713.48 , 5711.94 , 5710.4 , "$" , new Date(1508192499218L)));
        list.add(new USD(85 , 5708.22 , 5708.65 , 5708.22 , 5707.78 , "$" , new Date(1508194299032L)));
        list.add(new USD(86 , 5783.9 , 5784.4 , 5783.9 , 5783.4 , "$" , new Date(1508196099025L)));
        list.add(new USD(87 , 5757.94 , 5758.56 , 5757.94 , 5757.32 , "$" , new Date(1508197899015L)));
        list.add(new USD(88 , 5766.68 , 5767.26 , 5766.68 , 5766.11 , "$" , new Date(1508199699017L)));
        list.add(new USD(89 , 5755.38 , 5755.81 , 5755.38 , 5754.94 , "$" , new Date(1508201499038L)));
        list.add(new USD(90 , 5751.66 , 5752.39 , 5751.66 , 5750.93 , "$" , new Date(1508203300038L)));
        list.add(new USD(91 , 5647.12 , 5647.57 , 5647.12 , 5646.66 , "$" , new Date(1508205100048L)));
        list.add(new USD(92 , 5644.02 , 5644.26 , 5644.02 , 5643.78 , "$" , new Date(1508206899050L)));
        list.add(new USD(93 , 5634.96 , 5636.11 , 5634.96 , 5633.81 , "$" , new Date(1508208699051L)));
        list.add(new USD(94 , 5641.8 , 5642.84 , 5641.8 , 5640.76 , "$" , new Date(1508209088352L)));
        list.add(new USD(95 , 5632.38 , 5633.01 , 5632.38 , 5631.74 , "$" , new Date(1508209415745L)));
        list.add(new USD(96 , 5639.19 , 5639.32 , 5639.19 , 5639.07 , "$" , new Date(1508210499002L)));
        list.add(new USD(97 , 5627.59 , 5627.69 , 5627.59 , 5627.48 , "$" , new Date(1508211465911L)));
        list.add(new USD(98 , 5644.13 , 5644.24 , 5644.13 , 5644.02 , "$" , new Date(1508212299990L)));
        list.add(new USD(99 , 5652.58 , 5653.59 , 5652.58 , 5651.58 , "$" , new Date(1508214102320L)));
        list.add(new USD(100 , 5651.38 , 5652.35 , 5651.38 , 5650.41 , "$" , new Date(1508215899458L)));
        list.add(new USD(101 , 5624.18 , 5624.98 , 5624.18 , 5623.38 , "$" , new Date(1508217699253L)));
        list.add(new USD(102 , 5627.26 , 5628.02 , 5627.26 , 5626.5 , "$" , new Date(1508219499171L)));
        list.add(new USD(103 , 5641.49 , 5641.98 , 5641.49 , 5640.99 , "$" , new Date(1508221299242L)));
        list.add(new USD(104 , 5671.16 , 5672.71 , 5671.16 , 5669.61 , "$" , new Date(1508223099158L)));
        list.add(new USD(105 , 5663.03 , 5664.29 , 5663.03 , 5661.76 , "$" , new Date(1508224899207L)));
        list.add(new USD(106 , 5655.38 , 5655.99 , 5655.38 , 5654.76 , "$" , new Date(1508226699238L)));
        list.add(new USD(107 , 5660.56 , 5661.26 , 5660.56 , 5659.86 , "$" , new Date(1508228499245L)));
        list.add(new USD(108 , 5674.23 , 5674.87 , 5674.23 , 5673.6 , "$" , new Date(1508230299047L)));
        list.add(new USD(109 , 5675.05 , 5675.74 , 5675.05 , 5674.35 , "$" , new Date(1508232099056L)));
        list.add(new USD(110 , 5670.46 , 5671.3 , 5670.46 , 5669.62 , "$" , new Date(1508233899287L)));
        list.add(new USD(111 , 5671.54 , 5672.09 , 5671.54 , 5670.99 , "$" , new Date(1508235699430L)));
        list.add(new USD(112 , 5672.19 , 5673.07 , 5672.19 , 5671.31 , "$" , new Date(1508237499339L)));
        list.add(new USD(113 , 5637.28 , 5637.75 , 5637.28 , 5636.81 , "$" , new Date(1508239299074L)));
        list.add(new USD(114 , 5556.99 , 5557.95 , 5556.99 , 5556.02 , "$" , new Date(1508241099043L)));
        list.add(new USD(115 , 5523.51 , 5524.71 , 5523.51 , 5522.3 , "$" , new Date(1508242899045L)));
        list.add(new USD(116 , 5553.22 , 5554.06 , 5553.22 , 5552.37 , "$" , new Date(1508244248023L)));
        list.add(new USD(117 , 5565.37 , 5566.12 , 5565.37 , 5564.63 , "$" , new Date(1508244699084L)));
        list.add(new USD(118 , 5567.81 , 5568.15 , 5567.81 , 5567.46 , "$" , new Date(1508246499170L)));
        list.add(new USD(119 , 5587.26 , 5587.53 , 5587.26 , 5586.99 , "$" , new Date(1508248299091L)));
        list.add(new USD(120 , 5607.05 , 5607.38 , 5607.05 , 5606.72 , "$" , new Date(1508250099078L)));
        list.add(new USD(121 , 5606.0 , 5606.64 , 5606.0 , 5605.37 , "$" , new Date(1508250462638L)));
        list.add(new USD(122 , 5595.6 , 5596.25 , 5595.6 , 5594.94 , "$" , new Date(1508251899410L)));
        list.add(new USD(123 , 5570.6 , 5571.31 , 5570.6 , 5569.89 , "$" , new Date(1508252312600L)));
        list.add(new USD(124 , 5568.65 , 5569.28 , 5568.65 , 5568.02 , "$" , new Date(1508253699019L)));
        list.add(new USD(125 , 5575.71 , 5576.54 , 5575.71 , 5574.88 , "$" , new Date(1508253960345L)));
        list.add(new USD(126 , 5587.06 , 5587.6 , 5587.06 , 5586.52 , "$" , new Date(1508255499045L)));
        list.add(new USD(127 , 5585.96 , 5586.54 , 5585.96 , 5585.38 , "$" , new Date(1508257299111L)));
        list.add(new USD(128 , 5579.93 , 5580.61 , 5579.93 , 5579.25 , "$" , new Date(1508259099058L)));
        list.add(new USD(129 , 5580.71 , 5582.49 , 5580.71 , 5578.94 , "$" , new Date(1508260899089L)));
        list.add(new USD(130 , 5628.74 , 5629.41 , 5628.74 , 5628.06 , "$" , new Date(1508262699148L)));
        list.add(new USD(131 , 5627.74 , 5628.72 , 5627.74 , 5626.76 , "$" , new Date(1508264499229L)));
        list.add(new USD(132 , 5630.93 , 5631.79 , 5630.93 , 5630.07 , "$" , new Date(1508266299044L)));
        list.add(new USD(133 , 5615.74 , 5615.94 , 5615.74 , 5615.53 , "$" , new Date(1508268099007L)));
        list.add(new USD(134 , 5625.72 , 5626.55 , 5625.72 , 5624.88 , "$" , new Date(1508270122120L)));
        list.add(new USD(135 , 5628.77 , 5630.21 , 5628.77 , 5627.33 , "$" , new Date(1508271039817L)));
        list.add(new USD(136 , 5593.26 , 5594.3 , 5593.26 , 5592.23 , "$" , new Date(1508272719815L)));
        list.add(new USD(137 , 5582.09 , 5583.38 , 5582.09 , 5580.8 , "$" , new Date(1508274399826L)));
        list.add(new USD(138 , 5586.89 , 5587.81 , 5586.89 , 5585.96 , "$" , new Date(1508276079840L)));
        list.add(new USD(139 , 5573.42 , 5574.96 , 5573.42 , 5571.88 , "$" , new Date(1508277759841L)));
        list.add(new USD(140 , 5590.31 , 5592.2 , 5590.31 , 5588.41 , "$" , new Date(1508279439809L)));
        list.add(new USD(141 , 5569.9 , 5570.33 , 5569.9 , 5569.47 , "$" , new Date(1508281119836L)));
        list.add(new USD(142 , 5570.77 , 5571.52 , 5570.77 , 5570.02 , "$" , new Date(1508282799813L)));
        list.add(new USD(143 , 5595.83 , 5596.18 , 5595.83 , 5595.48 , "$" , new Date(1508284479806L)));
        list.add(new USD(144 , 5525.87 , 5527.0 , 5525.87 , 5524.74 , "$" , new Date(1508286159880L)));
        list.add(new USD(145 , 5521.0 , 5521.8 , 5521.0 , 5520.2 , "$" , new Date(1508287839865L)));
        list.add(new USD(146 , 5508.29 , 5508.84 , 5508.29 , 5507.73 , "$" , new Date(1508289519872L)));
        list.add(new USD(147 , 5506.04 , 5506.41 , 5506.04 , 5505.66 , "$" , new Date(1508291199810L)));
        list.add(new USD(148 , 5474.12 , 5474.93 , 5474.12 , 5473.3 , "$" , new Date(1508292879827L)));
        list.add(new USD(149 , 5502.28 , 5503.59 , 5502.28 , 5500.97 , "$" , new Date(1508294559895L)));
        list.add(new USD(150 , 5480.2 , 5480.63 , 5480.2 , 5479.76 , "$" , new Date(1508296239778L)));
        list.add(new USD(151 , 5466.92 , 5467.58 , 5466.92 , 5466.26 , "$" , new Date(1508297919818L)));
        list.add(new USD(152 , 5471.71 , 5472.31 , 5471.71 , 5471.11 , "$" , new Date(1508298147120L)));
        list.add(new USD(153 , 5513.28 , 5514.23 , 5513.28 , 5512.34 , "$" , new Date(1508299599943L)));
        list.add(new USD(154 , 5513.88 , 5514.8 , 5513.88 , 5512.97 , "$" , new Date(1508300255635L)));
        list.add(new USD(155 , 5388.85 , 5389.3 , 5388.85 , 5388.4 , "$" , new Date(1508348413122L)));
        list.add(new USD(156 , 5363.38 , 5363.93 , 5363.38 , 5362.82 , "$" , new Date(1508348592545L)));
        list.add(new USD(157 , 5373.15 , 5373.51 , 5373.15 , 5372.78 , "$" , new Date(1508348772412L)));
        list.add(new USD(158 , 5375.89 , 5376.19 , 5375.89 , 5375.59 , "$" , new Date(1508349030470L)));
        list.add(new USD(159 , 5370.97 , 5371.48 , 5370.97 , 5370.45 , "$" , new Date(1508349210035L)));
        list.add(new USD(160 , 5374.7 , 5375.45 , 5374.7 , 5373.95 , "$" , new Date(1508349390056L)));
        list.add(new USD(161 , 5376.38 , 5377.01 , 5376.38 , 5375.76 , "$" , new Date(1508349570060L)));
        list.add(new USD(162 , 5375.18 , 5376.23 , 5375.18 , 5374.14 , "$" , new Date(1508349750092L)));
        list.add(new USD(163 , 5366.18 , 5366.52 , 5366.18 , 5365.84 , "$" , new Date(1508349930016L)));
        list.add(new USD(164 , 5370.17 , 5370.61 , 5370.17 , 5369.74 , "$" , new Date(1508350325201L)));
        list.add(new USD(165 , 5392.76 , 5393.13 , 5392.76 , 5392.39 , "$" , new Date(1508351010540L)));
        list.add(new USD(166 , 5425.34 , 5425.97 , 5425.34 , 5424.71 , "$" , new Date(1508351729008L)));
        list.add(new USD(167 , 5427.5 , 5427.55 , 5427.5 , 5427.45 , "$" , new Date(1508351746777L)));
        list.add(new USD(168 , 5412.58 , 5413.56 , 5412.58 , 5411.6 , "$" , new Date(1508352568470L)));
        list.add(new USD(169 , 5403.63 , 5404.63 , 5403.63 , 5402.63 , "$" , new Date(1508353176438L)));
        list.add(new USD(170 , 5407.03 , 5407.12 , 5407.03 , 5406.94 , "$" , new Date(1508354210509L)));
        list.add(new USD(171 , 5389.13 , 5389.9 , 5389.13 , 5388.36 , "$" , new Date(1508355451297L)));
        list.add(new USD(172 , 5392.42 , 5393.01 , 5392.42 , 5391.82 , "$" , new Date(1508355634310L)));
        list.add(new USD(173 , 5408.45 , 5408.76 , 5408.45 , 5408.14 , "$" , new Date(1508355814396L)));
        list.add(new USD(174 , 5408.41 , 5408.86 , 5408.41 , 5407.96 , "$" , new Date(1508355994329L)));
        list.add(new USD(175 , 5396.14 , 5396.23 , 5396.14 , 5396.04 , "$" , new Date(1508356174340L)));
        list.add(new USD(176 , 5399.29 , 5399.73 , 5399.29 , 5398.84 , "$" , new Date(1508356354338L)));
        list.add(new USD(177 , 5397.06 , 5397.59 , 5397.06 , 5396.53 , "$" , new Date(1508356534327L)));
        list.add(new USD(178 , 5400.42 , 5400.65 , 5400.42 , 5400.18 , "$" , new Date(1508358359654L)));
        list.add(new USD(179 , 5493.9 , 5494.11 , 5493.9 , 5493.68 , "$" , new Date(1508360442297L)));
        list.add(new USD(180 , 5493.73 , 5494.25 , 5493.73 , 5493.21 , "$" , new Date(1508362637491L)));
        list.add(new USD(181 , 5507.45 , 5508.35 , 5507.45 , 5506.54 , "$" , new Date(1508364593259L)));
        list.add(new USD(182 , 5567.06 , 5567.54 , 5567.06 , 5566.57 , "$" , new Date(1508367365914L)));
        list.add(new USD(183 , 5595.09 , 5596.03 , 5595.09 , 5594.16 , "$" , new Date(1508370578367L)));
        list.add(new USD(184 , 5519.78 , 5520.33 , 5519.78 , 5519.22 , "$" , new Date(1508373908121L)));
        list.add(new USD(185 , 5585.1 , 5586.24 , 5585.1 , 5583.95 , "$" , new Date(1508375888209L)));
        list.add(new USD(186 , 5636.95 , 5637.53 , 5636.95 , 5636.36 , "$" , new Date(1508378006824L)));
        list.add(new USD(187 , 5627.05 , 5627.41 , 5627.05 , 5626.69 , "$" , new Date(1508380596562L)));
        list.add(new USD(188 , 5606.53 , 5607.48 , 5606.53 , 5605.58 , "$" , new Date(1508381747742L)));
        list.add(new USD(189 , 5606.53 , 5607.48 , 5606.53 , 5605.58 , "$" , new Date(1508381796474L)));
        list.add(new USD(190 , 5604.34 , 5604.58 , 5604.34 , 5604.11 , "$" , new Date(1508382783038L)));
        list.add(new USD(191 , 5609.91 , 5611.19 , 5609.91 , 5608.63 , "$" , new Date(1508382961750L)));
        list.add(new USD(192 , 5607.32 , 5607.67 , 5607.32 , 5606.97 , "$" , new Date(1508383141723L)));
        list.add(new USD(193 , 5600.58 , 5602.01 , 5600.58 , 5599.15 , "$" , new Date(1508383321719L)));
        list.add(new USD(194 , 5600.58 , 5602.01 , 5600.58 , 5599.15 , "$" , new Date(1508383501734L)));
        list.add(new USD(195 , 5614.82 , 5615.63 , 5614.82 , 5614.01 , "$" , new Date(1508383791044L)));
        list.add(new USD(196 , 5609.44 , 5610.81 , 5609.44 , 5608.07 , "$" , new Date(1508384355939L)));
        list.add(new USD(197 , 5615.55 , 5616.64 , 5615.55 , 5614.45 , "$" , new Date(1508384789903L)));
        list.add(new USD(198 , 5617.67 , 5619.08 , 5617.67 , 5616.25 , "$" , new Date(1508384969729L)));
        list.add(new USD(199 , 5643.06 , 5643.9 , 5643.06 , 5642.22 , "$" , new Date(1508386146517L)));
        list.add(new USD(200 , 5650.27 , 5650.43 , 5650.27 , 5650.1 , "$" , new Date(1508386922211L)));
        list.add(new USD(201 , 5651.08 , 5652.44 , 5651.08 , 5649.73 , "$" , new Date(1508388205602L)));
        list.add(new USD(202 , 5642.25 , 5642.87 , 5642.25 , 5641.62 , "$" , new Date(1508388231006L)));
        list.add(new USD(203 , 5642.53 , 5645.24 , 5642.53 , 5639.81 , "$" , new Date(1508388409187L)));
        list.add(new USD(204 , 5631.2 , 5632.11 , 5631.2 , 5630.28 , "$" , new Date(1508388934617L)));
        list.add(new USD(205 , 5631.2 , 5632.11 , 5631.2 , 5630.28 , "$" , new Date(1508389114371L)));
        list.add(new USD(206 , 5632.67 , 5633.02 , 5632.67 , 5632.33 , "$" , new Date(1508389809631L)));
        list.add(new USD(207 , 5611.15 , 5611.81 , 5611.15 , 5610.48 , "$" , new Date(1508392558853L)));
        list.add(new USD(208 , 5605.9 , 5606.36 , 5605.9 , 5605.44 , "$" , new Date(1508392773516L)));
        list.add(new USD(209 , 5587.87 , 5588.49 , 5587.87 , 5587.25 , "$" , new Date(1508392952827L)));
        list.add(new USD(210 , 5601.72 , 5601.92 , 5601.72 , 5601.52 , "$" , new Date(1508393132836L)));
        list.add(new USD(211 , 5615.92 , 5616.67 , 5615.92 , 5615.16 , "$" , new Date(1508393312882L)));
        list.add(new USD(212 , 5612.51 , 5612.58 , 5612.51 , 5612.45 , "$" , new Date(1508393492934L)));
        list.add(new USD(213 , 5615.13 , 5615.31 , 5615.13 , 5614.94 , "$" , new Date(1508393672930L)));
        list.add(new USD(214 , 5610.58 , 5611.06 , 5610.58 , 5610.1 , "$" , new Date(1508393852747L)));
        list.add(new USD(215 , 5608.0 , 5608.49 , 5608.0 , 5607.51 , "$" , new Date(1508394032755L)));
        list.add(new USD(216 , 5600.04 , 5600.81 , 5600.04 , 5599.27 , "$" , new Date(1508394212788L)));
        list.add(new USD(217 , 5598.01 , 5598.8 , 5598.01 , 5597.23 , "$" , new Date(1508394392742L)));
        list.add(new USD(218 , 5598.36 , 5598.8 , 5598.36 , 5597.91 , "$" , new Date(1508394572777L)));
        list.add(new USD(219 , 5598.91 , 5599.64 , 5598.91 , 5598.18 , "$" , new Date(1508394753008L)));
        list.add(new USD(220 , 5607.22 , 5608.89 , 5607.22 , 5605.56 , "$" , new Date(1508394932782L)));
        list.add(new USD(221 , 5599.18 , 5599.91 , 5599.18 , 5598.45 , "$" , new Date(1508395112777L)));
        list.add(new USD(222 , 5597.05 , 5597.51 , 5597.05 , 5596.59 , "$" , new Date(1508395293021L)));
        list.add(new USD(223 , 5597.24 , 5597.28 , 5597.24 , 5597.21 , "$" , new Date(1508395472775L)));
        list.add(new USD(224 , 5584.35 , 5585.2 , 5584.35 , 5583.49 , "$" , new Date(1508395652974L)));
        list.add(new USD(225 , 5567.44 , 5567.56 , 5567.44 , 5567.33 , "$" , new Date(1508395832709L)));
        list.add(new USD(226 , 5563.96 , 5564.41 , 5563.96 , 5563.5 , "$" , new Date(1508396012725L)));
        list.add(new USD(227 , 5568.39 , 5568.74 , 5568.39 , 5568.04 , "$" , new Date(1508396192723L)));
        list.add(new USD(228 , 5569.06 , 5569.4 , 5569.06 , 5568.71 , "$" , new Date(1508396372857L)));
        list.add(new USD(229 , 5566.76 , 5567.33 , 5566.76 , 5566.2 , "$" , new Date(1508397022072L)));
        list.add(new USD(230 , 5558.58 , 5558.94 , 5558.58 , 5558.23 , "$" , new Date(1508397110985L)));
        list.add(new USD(231 , 5558.67 , 5559.21 , 5558.67 , 5558.12 , "$" , new Date(1508397347559L)));
        list.add(new USD(232 , 5583.3 , 5583.62 , 5583.3 , 5582.98 , "$" , new Date(1508397527434L)));
        list.add(new USD(233 , 5585.69 , 5587.04 , 5585.69 , 5584.34 , "$" , new Date(1508397707390L)));
        list.add(new USD(234 , 5586.07 , 5586.61 , 5586.07 , 5585.52 , "$" , new Date(1508397887403L)));
        list.add(new USD(235 , 5584.09 , 5585.45 , 5584.09 , 5582.73 , "$" , new Date(1508398067427L)));
        list.add(new USD(236 , 5586.45 , 5587.85 , 5586.45 , 5585.04 , "$" , new Date(1508398247419L)));
        list.add(new USD(237 , 5586.44 , 5587.11 , 5586.44 , 5585.78 , "$" , new Date(1508398427392L)));
        list.add(new USD(238 , 5601.36 , 5602.38 , 5601.36 , 5600.33 , "$" , new Date(1508398607391L)));
        list.add(new USD(239 , 5598.67 , 5599.73 , 5598.67 , 5597.61 , "$" , new Date(1508398787406L)));
        list.add(new USD(240 , 5602.61 , 5603.17 , 5602.61 , 5602.04 , "$" , new Date(1508398995156L)));
        list.add(new USD(241 , 5604.72 , 5606.2 , 5604.72 , 5603.24 , "$" , new Date(1508399174583L)));
        list.add(new USD(242 , 5598.31 , 5599.67 , 5598.31 , 5596.94 , "$" , new Date(1508399354764L)));
        list.add(new USD(243 , 5599.21 , 5600.58 , 5599.21 , 5597.84 , "$" , new Date(1508399534589L)));
        list.add(new USD(244 , 5597.1 , 5598.18 , 5597.1 , 5596.02 , "$" , new Date(1508399714518L)));
        list.add(new USD(245 , 5596.47 , 5597.1 , 5596.47 , 5595.84 , "$" , new Date(1508399894870L)));
        list.add(new USD(246 , 5602.69 , 5603.6 , 5602.69 , 5601.77 , "$" , new Date(1508400074531L)));
        list.add(new USD(247 , 5589.44 , 5590.26 , 5589.44 , 5588.61 , "$" , new Date(1508400762513L)));
        list.add(new USD(248 , 5636.03 , 5636.28 , 5636.03 , 5635.77 , "$" , new Date(1508402522811L)));
        list.add(new USD(249 , 5630.41 , 5632.41 , 5630.41 , 5628.4 , "$" , new Date(1508402791672L)));
        list.add(new USD(250 , 5623.91 , 5624.23 , 5623.91 , 5623.58 , "$" , new Date(1508403874270L)));
        list.add(new USD(251 , 5607.33 , 5609.0 , 5607.33 , 5605.66 , "$" , new Date(1508404635929L)));
        list.add(new USD(252 , 5615.09 , 5615.65 , 5615.09 , 5614.52 , "$" , new Date(1508404815464L)));
        list.add(new USD(253 , 5622.78 , 5623.44 , 5622.78 , 5622.11 , "$" , new Date(1508404995673L)));
        list.add(new USD(254 , 5627.91 , 5628.38 , 5627.91 , 5627.43 , "$" , new Date(1508405175463L)));
        list.add(new USD(255 , 5636.22 , 5636.61 , 5636.22 , 5635.82 , "$" , new Date(1508405355418L)));
        list.add(new USD(256 , 5643.48 , 5643.54 , 5643.48 , 5643.41 , "$" , new Date(1508405535460L)));
        list.add(new USD(257 , 5645.95 , 5646.42 , 5645.95 , 5645.48 , "$" , new Date(1508405715483L)));
        list.add(new USD(258 , 5639.19 , 5639.26 , 5639.19 , 5639.12 , "$" , new Date(1508405895499L)));
        list.add(new USD(259 , 5637.84 , 5638.44 , 5637.84 , 5637.24 , "$" , new Date(1508406075471L)));
        list.add(new USD(260 , 5661.48 , 5661.92 , 5661.48 , 5661.04 , "$" , new Date(1508406255482L)));
        list.add(new USD(261 , 5691.75 , 5691.94 , 5691.75 , 5691.55 , "$" , new Date(1508406435468L)));
        list.add(new USD(262 , 5693.06 , 5693.79 , 5693.06 , 5692.33 , "$" , new Date(1508406615496L)));
        list.add(new USD(263 , 5680.88 , 5682.26 , 5680.88 , 5679.49 , "$" , new Date(1508406795894L)));
        list.add(new USD(264 , 5690.01 , 5691.28 , 5690.01 , 5688.74 , "$" , new Date(1508406975365L)));
        list.add(new USD(265 , 5691.58 , 5693.19 , 5691.58 , 5689.97 , "$" , new Date(1508407155283L)));
        list.add(new USD(266 , 5691.45 , 5692.67 , 5691.45 , 5690.23 , "$" , new Date(1508407335288L)));
        list.add(new USD(267 , 5691.78 , 5691.91 , 5691.78 , 5691.64 , "$" , new Date(1508407515362L)));
        list.add(new USD(268 , 5690.7 , 5691.07 , 5690.7 , 5690.33 , "$" , new Date(1508407695305L)));
        list.add(new USD(269 , 5670.9 , 5671.28 , 5670.9 , 5670.52 , "$" , new Date(1508407875277L)));
        list.add(new USD(270 , 5672.99 , 5673.91 , 5672.99 , 5672.07 , "$" , new Date(1508408055291L)));
        list.add(new USD(271 , 5669.03 , 5669.44 , 5669.03 , 5668.61 , "$" , new Date(1508408235335L)));
        list.add(new USD(272 , 5671.23 , 5671.43 , 5671.23 , 5671.02 , "$" , new Date(1508408415280L)));
        list.add(new USD(273 , 5671.34 , 5672.34 , 5671.34 , 5670.34 , "$" , new Date(1508408595535L)));
        list.add(new USD(274 , 5674.82 , 5675.63 , 5674.82 , 5674.02 , "$" , new Date(1508408775280L)));
        list.add(new USD(275 , 5681.46 , 5682.18 , 5681.46 , 5680.73 , "$" , new Date(1508408955293L)));
        list.add(new USD(276 , 5691.6 , 5693.12 , 5691.6 , 5690.08 , "$" , new Date(1508409135283L)));
        list.add(new USD(277 , 5711.64 , 5712.22 , 5711.64 , 5711.05 , "$" , new Date(1508409315298L)));
        list.add(new USD(278 , 5711.63 , 5712.37 , 5711.63 , 5710.89 , "$" , new Date(1508409495291L)));
        list.add(new USD(279 , 5709.11 , 5709.61 , 5709.11 , 5708.61 , "$" , new Date(1508409675350L)));
        list.add(new USD(280 , 5697.94 , 5699.18 , 5697.94 , 5696.7 , "$" , new Date(1508409855288L)));
        list.add(new USD(281 , 5697.05 , 5697.94 , 5697.05 , 5696.16 , "$" , new Date(1508410035308L)));
        list.add(new USD(282 , 5684.88 , 5686.0 , 5684.88 , 5683.76 , "$" , new Date(1508410215295L)));
        list.add(new USD(283 , 5685.34 , 5686.5 , 5685.34 , 5684.19 , "$" , new Date(1508410395278L)));
        list.add(new USD(284 , 5689.67 , 5690.41 , 5689.67 , 5688.92 , "$" , new Date(1508410575356L)));
        list.add(new USD(285 , 5685.53 , 5686.1 , 5685.53 , 5684.95 , "$" , new Date(1508410755279L)));
        list.add(new USD(286 , 5690.89 , 5691.71 , 5690.89 , 5690.06 , "$" , new Date(1508411180156L)));
        list.add(new USD(287 , 5698.27 , 5699.21 , 5698.27 , 5697.33 , "$" , new Date(1508412143538L)));
        list.add(new USD(288 , 5686.75 , 5688.37 , 5686.75 , 5685.12 , "$" , new Date(1508414854965L)));
        list.add(new USD(289 , 5657.42 , 5658.5 , 5657.42 , 5656.35 , "$" , new Date(1508415689521L)));
        list.add(new USD(290 , 5652.54 , 5653.73 , 5652.54 , 5651.35 , "$" , new Date(1508415868539L)));
        list.add(new USD(291 , 5663.68 , 5664.4 , 5663.68 , 5662.97 , "$" , new Date(1508416048509L)));
        list.add(new USD(292 , 5663.68 , 5664.4 , 5663.68 , 5662.97 , "$" , new Date(1508416228956L)));
        list.add(new USD(293 , 5669.09 , 5669.35 , 5669.09 , 5668.82 , "$" , new Date(1508416408670L)));
        list.add(new USD(294 , 5674.66 , 5674.72 , 5674.66 , 5674.6 , "$" , new Date(1508416588943L)));
        list.add(new USD(295 , 5673.89 , 5674.19 , 5673.89 , 5673.6 , "$" , new Date(1508416768531L)));
        list.add(new USD(296 , 5664.98 , 5665.66 , 5664.98 , 5664.3 , "$" , new Date(1508416948514L)));
        list.add(new USD(297 , 5673.8 , 5674.41 , 5673.8 , 5673.19 , "$" , new Date(1508417129047L)));
        list.add(new USD(298 , 5671.68 , 5672.14 , 5671.68 , 5671.23 , "$" , new Date(1508417308791L)));
        list.add(new USD(299 , 5672.62 , 5673.12 , 5672.62 , 5672.11 , "$" , new Date(1508417488761L)));
        list.add(new USD(300 , 5672.64 , 5673.18 , 5672.64 , 5672.1 , "$" , new Date(1508417668590L)));
        list.add(new USD(301 , 5663.09 , 5663.73 , 5663.09 , 5662.45 , "$" , new Date(1508417848598L)));
        list.add(new USD(302 , 5659.71 , 5660.55 , 5659.71 , 5658.86 , "$" , new Date(1508418028987L)));
        list.add(new USD(303 , 5696.49 , 5696.73 , 5696.49 , 5696.25 , "$" , new Date(1508419528826L)));
        list.add(new USD(304 , 5699.29 , 5699.71 , 5699.29 , 5698.86 , "$" , new Date(1508419708662L)));
        list.add(new USD(305 , 5683.19 , 5683.94 , 5683.19 , 5682.44 , "$" , new Date(1508419888661L)));
        list.add(new USD(306 , 5695.11 , 5695.67 , 5695.11 , 5694.54 , "$" , new Date(1508420069063L)));
        list.add(new USD(307 , 5691.81 , 5692.42 , 5691.81 , 5691.19 , "$" , new Date(1508420248838L)));
        list.add(new USD(308 , 5714.98 , 5715.4 , 5714.98 , 5714.55 , "$" , new Date(1508420428644L)));
        list.add(new USD(309 , 5715.43 , 5715.59 , 5715.43 , 5715.26 , "$" , new Date(1508420608893L)));
        list.add(new USD(310 , 5715.93 , 5716.04 , 5715.93 , 5715.83 , "$" , new Date(1508420788796L)));
        list.add(new USD(311 , 5710.82 , 5711.08 , 5710.82 , 5710.55 , "$" , new Date(1508420968771L)));
        list.add(new USD(312 , 5723.15 , 5723.45 , 5723.15 , 5722.85 , "$" , new Date(1508421148632L)));
        list.add(new USD(313 , 5725.13 , 5725.69 , 5725.13 , 5724.57 , "$" , new Date(1508421328897L)));
        list.add(new USD(314 , 5735.87 , 5736.51 , 5735.87 , 5735.22 , "$" , new Date(1508421508650L)));
        list.add(new USD(315 , 5736.72 , 5737.81 , 5736.72 , 5735.62 , "$" , new Date(1508421689105L)));
        list.add(new USD(316 , 5730.59 , 5732.13 , 5730.59 , 5729.05 , "$" , new Date(1508421968556L)));
        list.add(new USD(317 , 5723.4 , 5724.75 , 5723.4 , 5722.06 , "$" , new Date(1508424176913L)));
        list.add(new USD(318 , 5722.97 , 5723.94 , 5722.97 , 5721.99 , "$" , new Date(1508424667451L)));
        list.add(new USD(319 , 5706.58 , 5707.77 , 5706.58 , 5705.39 , "$" , new Date(1508425333373L)));
        list.add(new USD(320 , 5721.93 , 5724.74 , 5721.93 , 5719.12 , "$" , new Date(1508425513420L)));
        list.add(new USD(321 , 5720.67 , 5721.21 , 5720.67 , 5720.13 , "$" , new Date(1508425693441L)));
        list.add(new USD(322 , 5690.53 , 5691.62 , 5690.53 , 5689.44 , "$" , new Date(1508426448440L)));
        list.add(new USD(323 , 5639.94 , 5640.77 , 5639.94 , 5639.12 , "$" , new Date(1508429201294L)));
        list.add(new USD(324 , 5661.49 , 5662.69 , 5661.49 , 5660.28 , "$" , new Date(1508431098806L)));
        list.add(new USD(325 , 5667.22 , 5668.27 , 5667.22 , 5666.16 , "$" , new Date(1508432628806L)));
        list.add(new USD(326 , 5674.08 , 5674.88 , 5674.08 , 5673.28 , "$" , new Date(1508432807284L)));
        return list;
    }
}
