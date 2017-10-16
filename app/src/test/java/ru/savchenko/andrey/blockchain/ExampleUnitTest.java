package ru.savchenko.andrey.blockchain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.savchenko.andrey.blockchain.entities.USD;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void addition_isCorrect() throws Exception {
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
    }
}