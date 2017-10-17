package ru.savchenko.andrey.blockchain.repositories;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.Sort;
import ru.savchenko.andrey.blockchain.base.BaseAdapter;
import ru.savchenko.andrey.blockchain.base.BaseRepository;
import ru.savchenko.andrey.blockchain.entities.USD;

/**
 * Created by Andrey on 12.10.2017.
 */

public class USDRepository {
    private Realm realmInstance() {
        return Realm.getDefaultInstance();
    }

    public void writeIdDb(USD usd){
        BaseRepository baseRepository = new BaseRepository<>(USD.class);
        usd.setId(baseRepository.getMaxIdPlusOne());
        usd.setDate(new Date());
        baseRepository.addItem(usd);
    }

    public int writeIdDbReturnInteger(USD usd){
        BaseRepository baseRepository = new BaseRepository<>(USD.class);
        int maxId = baseRepository.getMaxIdPlusOne();
        usd.setId(maxId);
        usd.setDate(new Date());
        baseRepository.addItem(usd);
        return maxId;
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

    public List<USD>getLastThreeValues(){
        List<USD>usds = new ArrayList<>();
        List<USD>all = realmInstance().where(USD.class).findAllSorted("id", Sort.DESCENDING);
        realmInstance().close();
        for (int i = 0; i < 3; i++) {
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
        return list;
    }
}
