/**
 * Date: Mar 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * String工具类
 * 
 * @author jiayi.wei
 */
public class RandomPlayerNameUtil
{
    public static void main(String[] args)
    {
        List<String> list = new ArrayList<String>();
        System.out.println(list.get(2));
    }

    public static final String familyNames[] = { "·阿纳尔", "·埃金斯", "·埃里森",
            "·巴罗夫", "·巴罗伊", "·贝尔瓦", "·达伦德", "·达斯德", "·戴维德", "·德纳拉", "·阿什比",
            "·铁手", "·柔壤", "·轰脑", "·熔燃", "·钢火", "·血印", "·白蹄", "·弗彻", "·风刃",
            "·暗陷", "·弓叶", "·古风", "·希普", "·影矛", "·永日", "·羽月", "·北木", "·月林",
            "·棘甲", "·暗月", "·草风", "·石塔", "·炙痕", "·嘶唾", "·暗纹", "·暗织", "·轻风",
            "·雷蹄", "·浓须", "·巴纳比", "·毕若特", "·布莱葛", "·德斯", "·杜布斯", "·法伯尼",
            "·防火棉", "·费尔本", "·弗雷明", "·钢眉", "·鲍什", "·迅风", "·野蹄", "·追雨", "·战痕",
            "·塔布雷", "·织风", "·影击", "·曲针", "·布兰科", "·暴拳", "·爆哮", "·悲晨", "·悲歌",
            "·悲日", "·碧眼", "·冰壁", "·草蹄", "·晨风", "·赤刃", "·暴雨", "·傲刃", "·森加斯",
            "·麻疯", "·法奥克", "·深刺", "·月光", "·珀叶", "·幻影", "·枫心", "·波塔尔", "·布莱克",
            "·布雷因", "·杜洛曼", "·格瑟纳", "·吉波克", "·卡尔森", "·卡加德", "·科宾其", "·克莱尔",
            "·晨星", "·狂焰", "·烁狂", "·影星", "·犹根", "·邪指", "·玛格", "·霜影", "·伏根",
            "·奥卡尔", "·锤趾", "·黑钳", "·旋轴", "·泥巴", "·月角", "·火鬃", "·血啸", "·雨山",
            "·铁钩", "·重槌", "·锤足", "·火枝", "·桑普森", "·旋星", "·深林", "·汉斯", "·朗绍尔",
            "·鹰月", "·影林", "·凯林", "·达尼斯", "·哈里", "·魂痕", "·熟麦", "·雷夫瑞", "·米洛奇",
            "·波恩顿", "·萨纳森", "·萨恩", "·卡尔文", "·戴夫", "·戴林", "·祷日", "·道森", "·地语",
            "·电扳", "·凋蹄", "·冬灵", "·冬咏", "·独狼", "·德姆西", "·赫尔肖", "·哥布尔", "·佩尼",
            "·盖比", "·碾轮", "·硬心", "·唤法者", "·暗行者", "·哈蒙德", "·毒芽", "·钢轴", "·雷矛",
            "·泥泡", "·燧火", "·影止", "·灰云", "·风弓", "·苍尘", "·钢须", "·断脉", "·锻锤",
            "·堕歌", "·恩斯", "·飞沫", "·风磨", "·风心", "·锋刃", "·福德", "·斧击", "·法隆",
            "·黑臂", "·转啃", "·火纺", "·白角", "·焚化者", "·弗丁", "·卡扎克", "·地狱火", "·纳尔",
            "·风羽", "·麦风", "·幼蹄", "·哈德恩", "·泰隆纳", "·雷米克", "·战锤", "·巴坎", "·石眉",
            "·麦酒", "·福古斯", "·格古特", "·格里尔", "·古安特", "·哈里森", "·胡德森", "·霍兰德",
            "·金卡拉", "·卡利兰", "·卡温顿", "·腐颅", "·富勒", "·钢蹄", "·钢掌", "·钢指", "·格雷",
            "·钩喉", "·光轮", "·哈肯", "·海林", "·钢架", "·雪晨", "·晨行者", "·月晨", "·河风",
            "·黑石", "·星歌", "·刃叶", "·考伦", "·鹰爪", "·高索姆", "·弗勒", "·卡萨斯", "·沃克",
            "·博斯克", "·帕提德", "·布雷尔", "·粗臂", "·沃农", "·萨叶尔", "·根德瑞", "·哈罗斯",
            "·哈略特", "·荒原", "·基沙恩", "·吉塞提", "·卡拉格", "·卡特尔", "·李格斯", "·陆行者",
            "·古迪", "·轰隆", "·长耳", "·怒锤", "·蓝空", "·拉吉", "·柳月", "·硬骨", "·强击",
            "·火拳", "·骇斩", "·寒晨", "·寒刃", "·汉比", "·河影", "·黑谷", "·黑棘", "·黑烬",
            "·黑脉", "·黑山", "·黑轮", "·卡加尔", "·菲斯", "·甘特", "·蛮鬃", "·钢手", "·星风",
            "·唤雷", "·纳希亚", "·月刃", "·黑心", "·黑鸦", "·虎噬", "·话衣", "·唤焰", "·灰刃",
            "·辉盾", "·火枪", "·火刃", "·火纹", "·火歌", "·温特斯", "·石蹄", "·风语", "·铁角",
            "·硬蹄", "·晨鹰", "·跳鹿", "·暗日", "·石角", "·吉布", "·吉恩", "·棘风", "·棘杖",
            "·迦特", "·剑吼", "·箭泉", "·金靶", "·烬掌", "·警风", "·尖叶", "·机簧", "·虫林",
            "·斑皮", "·堤薇恩", "·雷管", "·考尔", "·德沃尔", "·逐星", "·铁枝", "·静息", "·酒拳",
            "·酒桶", "·攫爪", "·卡齿", "·卡拉", "·卡农", "·凯恩", "·凯尔", "·克雷", "·科普兰",
            "·克斯勒", "·库尼斯", "·利奇班", "·麦罗德", "·蒙托耶", "·奈特班", "·诺里斯", "·皮尔森",
            "·钱伯斯", "·科雅", "·乌鲁森", "·腐蹄", "·双月", "·韧皮", "·长牙", "·猎齿", "·森古特",
            "·麦姆", "·海瓦尔", "·克罗雷", "·布拉普", "·影石", "·扁蹄", "·雷酒", "·法席恩", "·琥珀",
            "·哈丁", "·天步", "·林", "·苦石", "·快械", "·狼眼", "·雷歌", "·雷爪", "·雷鬃",
            "·里佐", "·厉须", "·亮槽", "·猎日", "·拉波尼", "·兰萨尔", "·玛尔维", "·麦克基", "·米尔斯",
            "·奈希德", "·皮尔斯", "·普斯特", "·裘妮丝", "·史塔克", "·狼爪", "·高山", "·高行者",
            "·夺天者", "·铁颅", "·双碾", "·快乐钳", "·碾锁", "·光面", "·血牙", "·林恩", "·灵心",
            "·鲁姆", "·掠影", "·罗森", "·洛根", "·落日", "·绿足", "·马修", "·麦击", "·流水",
            "·飘发", "·暗鼻", "·波兰", "·石纹", "·速火", "·血爪", "·炎击", "·硬皮", "·月火",
            "·麦克利", "·刺鬃", "·闪钢", "·螺旋", "·速轰", "·落锤", "·月行者", "·雷尔", "·伊根",
            "·斯通", "·蛮心", "·盲眼", "·梅尔", "·煤心", "·灭碎", "·磨旋", "·魔行", "·魔奴",
            "·魔灾", "·莫兰", "·蒙佐尔", "·摩洛克", "·普隆德", "·瑞克斯", "·斯蒂恩", "·斯瑞德",
            "·斯坦内", "·塔尔松", "·唐纳德", "·特兰斯", "·米莱德", "·刺背", "·荒野", "·焰阻", "·艾斯",
            "·轮滑", "·锤子", "·银齿", "·玛基", "·史派基", "·莫勒", "·霜甲", "·射日者", "·晨光",
            "·凯利", "·沙伊尔", "·迷雾", "·利刃", "·明月", "·夜翼", "·莫雷", "·暮行", "·穆克",
            "·奈恩", "·奈尔", "·怒印", "·殴底", "·帕格", "·牌暴", "·泡泡", "·暮语", "·明林",
            "·喷酸", "·尤瑞克", "·札恩", "·萨托斯", "·青藤", "·银林", "·月落", "·铁心", "·纳斯",
            "·疾风", "·暴风爪", "·巨蹄", "·托博尔", "·帕林", "·钢皮", "·咕噜", "·熊皮", "·远箭",
            "·凝眉", "·河鬃", "·芬格", "·点火器", "·油指", "·霜掷", "·扭扳", "·深石", "·德林顿",
            "·伊瓦", "·诺尔", "·布鲁顿", "·格温", "·莫莱", "·冻石", "·科玛", "·暖风", "·塔希尔",
            "·鹰风", "·雷角", "·佩吉", "·劈簧", "·拼壶", "·珀星", "·破风", "·破魔", "·切颅",
            "·轻歌", "·燃光", "·日匕", "·燃羽", "·希斯", "·释影", "·短线", "·尘链", "·火眼",
            "·石锤", "·塔伦", "·东溪", "·雪衣", "·日怒", "·日心", "·柔掌", "·三杯", "·杉影",
            "·闪套", "·石盾", "·石环", "·石皮", "·石语", "·萨丁", "·约根", "·约翰森", "·布鲁克",
            "·费伦斯", "·血羽", "·杜耶尔", "·柔歌", "·长爪", "·艾隆汉", "·萨苏克", "·斯考恩",
            "·斯克纳", "·斯莱舍", "·斯塔基", "·斯特林", "·威尔逊", "·韦尔奇", "·希克斯", "·希瑞克",
            "·深痕", "·捷根", "·硬木", "·秋谷", "·鞭柄", "·冰角", "·埃杜恩", "·安德森", "·安尼斯",
            "·安威玛", "·石鹿", "·石刃", "·石趾", "·天影", "·铜栓", "·星语", "·雪蹄", "·里翁",
            "·赫温", "·隆", "·石轮", "·布克", "·奥雷", "·穆达尔", "·哈洛兰", "·韦斯特", "·米勒",
            "·夜弓", "·鸦爪", "·石钳", "·实橡", "·噬铁", "·双刃", "·霜风", "·霜角", "·霜链",
            "·霜流", "·霜蹄", "·霜心", "·水旗", "·水仪", "·死界", "·死械", "·松木", "·苏拉",
            "·速射", "·碎柄", "·碎盾", "·碎脉", "·碎怒", "·苏萨隆", "·索拉什", "·索洛伊", "·瓦纳姆",
            "·威利斯", "·沃特尔", "·细语者", "·寻路者", "·冰蹄", "·怒痕", "·碎日", "·碎月", "·燧刃",
            "·索顿", "·天蹄", "·跳水", "·铁护", "·铁枪", "·铁拳", "·铜钻", "·剃刀", "·贝德",
            "·迅锤", "·暗矢", "·拜尔", "·光流", "·杰特", "·烬须", "·淑月", "·棘痕", "·铁线",
            "·铁衣", "·瓦德", "·沃宁迪", "·崖行者", "·夜行者", "·影歌", "·影行者", "·远行者",
            "·祖莱尔", "·望日", "·韦伯", "·维沃", "·沃尔", "·夏普", "·橡夹", "·邪语", "·星爆",
            "·星盔", "·星刃", "·橡语者", "·绿树", "·秋光", "·史塔特", "·恒影", "·野木", "·血眼",
            "·霍华德", "·林德", "·滑链", "·亵渎者", "·踏潮者", "·韦斯温", "·瑞文", "·怒风", "·铜须",
            "·弗塔根", "·梦行者", "·风行者", "·塞隆", "·星杖", "·雪花", "·血嗥", "·血怒", "·血刃",
            "·血日", "·血污", "·血斩", "·迅刺", "·迅弧", "·旋杆", "·裂皮", "·阔叶", "·冬幽",
            "·系锤", "·垂旋", "·架引", "·腾斯", "·钩锤", "·罗阿", "·雪崩", "·阿维利", "·大炸弹",
            "·电机", "·恐怖", "·轮拧", "·塞克斯", "·扭钳", "·火锤", "·静嚎", "·迅箭", "·迅石",
            "·迅掌", "·驯魔", "·鸦林", "·牙脉", "·岩堂", "·焰锤", "·焰击", "·钥石", "·鸦眼",
            "·银须", "·旅人", "·闷棍", "·夏月", "·雷心", "·满油", "·钢拳", "·死门", "·雷瓦",
            "·扬勒夫", "·汉娜", "·哀刃", "·艾顿", "·暗嗥", "·暗篷", "·暗褶", "·白山", "·拜比",
            "·雹击", "·野歌", "·夜矛", "·议价", "·疫钢", "·疫语", "·银光", "·银怒", "·饮雨",
            "·鹰击", "·影刃", "·叶生", "·雷云", "·登格", "·火须", "·深怒", "·静语", "·费因",
            "·星坠", "·真发", "·文", "·影日", "·羽足", "·雨歌", "·雨光", "·语风", "·狱锤",
            "·月牙", "·云诗", "·灾心", "·斩石", "·硬币", "·恩戴尔", "·齿轮", "·沙旋", "·沃松",
            "·艾伦", "·锤暴", "·贝克利", "·掌劈", "·铁桩", "·硬凿", "·欢跳", "·震栓", "·风歌",
            "·飞轮", "·怒瞪", "·深炉", "·白云", "·达勒", "·火盟", "·羽弓", "·织铁", "·织发",
            "·日誓", "·晨歌", "·利刀", "·誓日", "·轻月", "·费恩", "·风箱", "·远行", "·暗雷",
            "·糟鼻", "·温斯顿", "·夜语", "·白爪", "·月风", "·哨毁", "·佛斯", "·碾骨", "·月升",
            "·锐角", "·铁腭", "·血滴", "·鹰翼", "·石须", "·黄眼", "·矿光", "·怒牙", "·铁哨",
            "·云鬃", "·钢炉", "·快刀", "·月影", "·静水", "·血泡", "·夜歌", "·枭翼", "·怒疤",
            "·棒柄", "·长线", "·凯尔东", "·曦光", "·柔风", "·夜风", "·莫格什", "·蜡烛", "·锈钳",
            "·摇轮", "·沃格姆" };

    public static final String nameMan[] = { "阿波罗", "阿诺德", "阿道夫", "阿尔杰",
            "阿尔杰农", "阿尔娃拉丁", "阿尔瓦", "亚历克斯", "阿尔文", "阿姆斯壮", "阿诺", "阿奇柏德", "阿奇尔",
            "埃达", "埃德加", "埃迪", "埃尔韦拉", "埃尔维斯", "埃里克", "埃玛", "埃文", "艾比盖", "艾伯特",
            "阿尔弗雷德", "艾布纳?", "艾布特", "艾德里安", "艾登/艾丹", "艾德蒙", "艾德文", "艾尔玛",
            "艾富里", "艾理斯", "艾略特", "艾谱莉拉丁", "艾萨克", "艾塞亚", "艾丝特希伯", "艾文", "艾西",
            "爱得拉", "爱得莱德", "爱德华", "爱迪生", "爱尔柏塔", "爱尔顿", "爱尔玛", "爱格伯特", "爱莉克希亚",
            "爱罗伊", "爱曼纽", "安得烈", "安德烈亚", "安东莞", "安德鲁", "安迪", "安东尼奥", "安纳贝尔",
            "安格斯", "安东尼", "安其罗", "安斯艾尔", "奥布里", "奥德里奇", "奥尔瑟雅", "奥古斯汀", "奥劳拉",
            "奥利弗", "奥斯顿", "奥斯卡", "奥斯汀", "本", "奥特曼", "巴德", "巴顿", "巴尔克", "巴克",
            "巴里", "巴里特", "巴伦", "巴罗", "巴奈特", "巴萨罗穆", "巴特", "巴特莱", "巴泽尔", "柏得温",
            "柏格", "柏特莱姆", "柏宜斯", "拜尔德", "拜伦", "班克罗福特", "班奈特", "班尼迪克", "宝儿",
            "保罗", "贝克", "鲍伯", "鲍比", "贝克汉姆", "贝齐", "本杰明", "伯特", "本森", "比尔",
            "比利", "布莱克", "比其尔", "彼得", "毕维斯", "毕夏普", "宾", "波顿", "波文", "伯顿",
            "伯里斯", "伯尼", "博格斯", "布德", "布拉得里克", "布莱迪", "布拉德", "布兰登", "布莱兹",
            "布兰得利", "布赖恩", "布朗", "布兰特", "布伦特", "布雷尔", "布里奇特", "布鲁克", "布鲁诺",
            "布鲁斯", "迦勒", "布尼尔", "布兹", "查德", "达尔文", "查尔斯", "采尼", "达尔西", "达伦",
            "达尼尔", "大卫", "戴夫", "戴纳", "丹尼", "丹尼尔", "丹尼丝", "丹尼斯", "丹普斯", "道格拉斯",
            "德博拉希伯", "德里克", "德维特", "德文", "邓肯", "狄克", "迪得莉盖尔", "迪恩", "迪伦", "迪姆",
            "蒂莫西", "杜克", "杜鲁", "多洛雷斯", "多明尼卡", "多明尼克", "范", "菲比", "菲蕾德",
            "菲力克斯", "菲利普", "斐迪南", "费恩", "费力克斯", "费奇", "费兹捷勒", "费滋", "弗恩",
            "弗兰克", "弗兰克思", "弗朗西斯", "弗莉达", "弗罗拉", "弗农", "弗瑞德", "福特", "富宾恩",
            "富兰克林", "盖", "盖尔", "高达", "高德佛里", "戈登", "格吉尔", "格拉迪斯", "格林", "格林顿",
            "格罗佛", "格纳", "葛里菲兹", "葛列格", "葛列格里", "葛瑞丝", "古斯塔夫", "哈帝", "哈乐德",
            "哈里森", "哈里特", "哈利", "哈伦", "哈瑞斯", "哈威", "海顿", "海勒", "海洛伊丝", "海曼",
            "韩弗理", "汉克", "汉米敦", "汉特", "赫伯特", "赫达", "赫尔曼", "赫瑟尔", "亨利", "华纳",
            "霍伯特", "霍尔", "霍根", "霍华德", "基诺", "吉伯特", "吉蒂", "吉恩", "吉罗德", "吉米",
            "吉姆", "吉榭尔", "加百利", "加比", "加尔", "加菲尔德", "加里", "加文", "嘉比里拉", "贾艾斯",
            "贾斯特", "贾斯汀", "杰弗里", "杰弗瑞", "杰克", "杰克逊", "杰奎琳", "杰拉尔", "杰罗姆", "杰瑞",
            "杰瑞德", "杰西", "杰伊", "卡尔文", "卡萝", "卡玛", "卡洛斯", "凯里", "卡梅伦", "卡尔",
            "卡斯帕", "塞西", "卡特", "凯尔", "凯尔", "凯理", "凯伦", "凯撒", "凯斯", "凯文", "凯希",
            "凯伊", "康拉德", "康那理惟士", "康奈尔", "康斯坦丝", "考伯特", "考尔比", "柯帝士", "柯利弗",
            "柯利弗德", "科迪", "科尔", "科林", "科兹莫", "克拉克", "克拉伦斯", "克莱得", "克莱儿",
            "克莱曼", "克莱斯特", "克莱凸", "克劳德", "克雷尔", "克雷孟特", "克里斯", "克里斯蒂安",
            "克里斯多夫", "克里斯廷", "克利夫兰", "克洛怡", "肯", "肯尼", "肯尼迪", "肯尼斯", "寇里",
            "昆廷", "拉里", "莱斯利", "赖安", "兰德尔", "兰迪", "兰斯", "劳伦", "劳伦斯", "劳伦特",
            "劳瑞", "劳瑞恩", "雷", "雷奥", "雷克斯", "雷纳德", "雷欧", "李", "李莉斯", "里奇",
            "理查德", "利安德尔", "利奥波特", "列得", "林赛", "柳真", "卢卡", "卢卡斯", "卢克", "鲁宾逊",
            "路易斯", "璐易丝", "伦道夫", "罗宾", "罗伯特", "罗杰", "罗伦", "罗纳德", "罗文", "罗伊",
            "洛克", "洛兰", "马丁", "马尔斯", "马科斯", "马克", "马克斯", "马克西米连", "马库斯", "马文",
            "马西", "马歇尔", "马修", "玛德琳", "玛吉", "玛佩尔", "迈克尔", "麦克", "梅森", "米奇",
            "穆得", "纳撒尼尔", "内森", "尼尔", "尼尔森", "尼古拉斯", "尼科拉斯", "尼克", "诺曼", "诺亚",
            "欧尼斯特", "欧文", "帕特里克", "契布曼", "强尼", "乔", "乔丹", "乔蒂", "乔纳森", "乔尼",
            "乔治", "瑞利", "萨米", "萨姆", "塞缪尔", "塞西莉亚", "赛得里克", "圣扎迦利", "史蒂夫",
            "史蒂文", "斯宾塞", "斯考特", "斯帕克", "斯派克", "斯坦利", "斯图尔特", "斯图亚特", "所罗门",
            "泰德", "泰勒", "汤米", "汤姆", "唐恩", "唐纳德", "特里", "特伦斯", "提姆", "潼恩", "托德",
            "托马斯", "托尼", "威利", "威廉", "韦恩", "维克多", "卫斯理", "文森特", "沃伦", "西德尼",
            "西莱斯特", "西蒙", "西瑞尔", "希尔顿", "夏洛特", "夏佐", "肖恩", "晓恩", "谢里尔", "休伯特",
            "修", "雅各布", "雅尼克", "亚伯", "亚伯拉罕", "亚伯拉罕", "亚当", "亚岱尔", "亚度尼斯",
            "亚尔林", "亚尔曼", "亚尔维斯", "亚恒", "亚力士", "亚伦", "亚历山大", "艾伦", "亚伦", "亚伯",
            "亚摩斯", "亚撒", "亚瑟", "奥古斯特", "亚特兰特", "亚特伍德", "亚希伯恩", "伊登", "伊迪丝",
            "伊甸", "伊恩", "伊凡", "伊夫力", "伊格纳缇伍兹", "伊莱", "伊莱哲", "伊诺克", "伊桑", "以利亚",
            "尤利安", "尤利塞斯", "雨果", "约翰", "约瑟芬", "约瑟夫", "约书亚", "扎克", "詹姆士", "詹姆斯",
            "詹森", "朱恩" };

    public static final String nameWoman[] = { "安伯", "阿纳斯", "塔西娅", "索非娅",
            "爱葛妮丝", "艾丽斯", "昆蒂娜", "艾丽莎", "艾米", "阿蜜莉雅", "卡拉", "绮莉", "贝丝", "百丽儿",
            "帕姬", "克莉丝", "克洛伊", "苏", "克劳迪娅", "克莱门特", "萨妮", "康斯坦斯", "珀尔",
            "艾丽西娅", "艾琳娜", "苏菲", "艾美丽", "艾德琳", "亚历桑德拉", "克里斯汀", "辛迪", "丝特拉",
            "达莲娜", "玛德琳?", "克莱尔", "瑞加娜", "伊妮德", "埃尔莎", "苏珊", "克莱拉", "斯蒂芬妮",
            "菲洛米娜", "菲比", "克里斯蒂", "克里斯蒂娜", "菲丽丝", "曼蒂?", "玛姬?", "莉蒂亚?", "波莉",
            "梅蜜?", "玛西娅", "爱勒贝拉", "雷切尔", "柏妮丝", "艾莉森", "艾莉莎", "柏莎", "丽贝卡",
            "阿曼达", "艾美", "芭芭拉", "贝琳达", "黛娜", "阿加莎", "普里西拉", "黛芙妮", "罗丝", "朱恩",
            "爱玛", "凯琳", "安吉拉", "安吉莉亚", "米歇尔", "多莉丝", "尤妮斯", "阿莎加尔", "艾薇儿",
            "爱尔莎", "艾琳诺", "伊莲恩", "尔莎", "艾德文娜", "埃米莉", "艾娃", "尤多拉", "伊文捷琳",
            "迪丽雅", "艾薇", "莉迪亚", "瑞文", "黛碧", "艾希", "多琳", "伊莎蓓尔", "爱莉丝", "姬玛",
            "卡蒂", "汉妮", "艾莉尔", "阿普里尔", "希拉瑞莉", "洛克萨妮", "安吉莉娜", "安", "安娜", "安妮",
            "安倪", "安尼塔", "梵妮", "卡特里娜", "玛琪", "米娜", "戴安娜", "艾梦儿", "莉莎", "利昂娜",
            "艾莎", "萝拉", "艾斯妮", "露西", "珍妮芙", "伊莎贝尔", "艾许莉", "欧蕊", "露丝", "伊莎瑞尔",
            "芭比", "贝亚特", "阿维娃", "笆笆拉", "米琪", "萨莉", "桑德拉", "艾文", "比阿特丽斯", "贝基",
            "阿莎利亚", "迪莉雅", "伊芙琳", "卡桑德拉", "布伦达", "布莱安娜", "莎娜", "艾薇尔", "艾芙琳",
            "凯西", "艾薇莱尔", "蒙多莉亚", "凯莉", "凯丽", "斯莱瑞", "凯瑟琳", "伊莎薇儿", "夏洛特",
            "雪莉", "凯茜", "璐易丝?", "阿黛儿", "尤露莱特", "切尔西", "雪丽", "伊莎黛儿", "雪莉尔",
            "奥利维亚", "梅甘", "切莉", "尤娜莱特", "西尔维亚", "索尼亚", "苏妮莉亚", "米兰达", "凯拉",
            "特莉萨", "莎薇德拉?", "凯", "芝妮雅?", "奥菲莉娅", "奥帕", "耶达?", "依耶塔", "塔玛拉",
            "米里亚姆", "科瑞斯特尔", "科瑞恩", "谭雅坦尼娅", "塔莎", "苔米", "茉莉", "达芙妮", "科拉",
            "玛莎?", "戴茜", "苏菲尔", "萨布丽娜", "狄安娜", "厄里斯", "赫迪娜", "珍尼丝", "棠雅", "堂娜",
            "多拉", "卡莎琳娜", "朱丽叶", "卡列尼娜", "赫墨拉", "厄瑞妮", "特蕾西", "苏姗", "桃瑞丝",
            "锐克丝", "伊迪丝", "艾维尼亚", "伊迪萨", "克丽丝", "海尔", "弗丽嘉", "伊莱恩", "艾斯特莱雅",
            "维纳斯", "伊雪儿", "希尔顿", "埃拉", "依娃", "伊迈尔", "斯卡娣", "温妮莎", "奥尔布达",
            "厄休拉", "艾莉", "南茜", "瑞娅", "娅娃", "芙丽娅", "爱伦", "德洛丽丝", "曼妮", "旺达",
            "伊芳", "诺伊尔", "弗罗伦丝", "福罗拉", "蒂娜", "达茜", "戴夫", "依耶芙特", "黛比", "黛博拉",
            "黛布拉", "苔丝", "米洛娃", "迦娜", "格瑞丝", "黛米", "伊夫林", "埃莉卡", "爱斯特尔", "维拉",
            "蒂凡妮", "芬妮", "费怡", "迈雪儿", "菲奥纳", "伊芙", "黛安妮", "萨曼莎", "维吉妮亚", "贝蒂",
            "贝迪", "贝拉", "贝斯", "布兰奇", "邦妮", "微微妮", "海伦", "雅妮诗娜", "伊莎妮尔", "维达",
            "蒙娜丽莎", "格瑞塔", "海伦娜", "凯特蕾尔", "薇薇安", "伊斯乔娜", "希维尔", "薇莎", "莎伦",
            "伊莎迦娜", "休妮诗特", "薇娜", "坎蒂", "锐雯", "卡瑞娜", "卡门", "凯罗尔", "卡罗琳", "莰蒂丝",
            "伊菲尔", "伊莎迦尔", "塞琳娜", "塞丽娜", "卡特琳娜", "薇恩", "伊莎妮娅", "卡莎尼娅", "伊梦尔",
            "布兰妮", "布列塔尼", "卡米尔", "塞尔玛", "艾莎蕾尔", "斯嘉丽", "格温多琳", "赫柏", "汉娜",
            "海莉", "乔娜", "凯尔西", "海蒂", "杰奎琳", "露西娅", "特里娜", "莱瑞拉", "汉纳", "卓拉",
            "英格丽德", "希拉里", "小玉", "詹米", "蕾西", "尼迪亚", "莉娜", "艾丽丝", "艾琳", "爱沙拉",
            "韦恩", "莉莲", "莉迪娅", "伊莎贝拉", "艾维", "罗兰", "惠特尼", "康妮", "奥琳娜", "奥克塔维亚",
            "珍妮特", "丽莎", "简", "尤兰达", "基蒂", "琳赛", "琳达", "洛拉", "温蒂", "罗琳", "路易莎",
            "路易丝", "莉兹", "温妮", "杰西", "詹妮", "詹妮弗", "珍娜", "乔斯林", "帕梅拉", "贾斯敏",
            "姬尔", "姬恩", "杰西卡", "乔安娜", "伊温妮", "塞尔达", "伊薇特", "乔莉埃特", "乔茜", "琼",
            "约瑟芬", "佐伊", "劳拉", "乔伊", "乔伊斯", "朱迪丝", "朱蒂", "芭迪", "朱莉娅", "朱莉",
            "朱莉安娜", "玛丽安", "卡瑞达", "凯特", "艾瑞莉亚", "艾莎尼娅", "弗郎西丝", "吉莉安", "维姬",
            "吉娜", "弗里达", "黛安娜", "莉莉", "露西妮", "露茜", "露露", "萨米", "凯特琳", "诺玛",
            "玛莎", "梅维丝", "玛格丽特", "林恩", "曼迪", "玛丽亚", "玛丽莲", "玛丽", "玛蒂尔达", "曼达",
            "梅", "米娅", "梅利莎", "美洛蒂", "艾莎莱特", "沙琳", "玛克辛", "莫琳", "梅米", "希拉",
            "梅琳达", "默西迪丝", "梅瑞狄斯", "尼娜", "尼基塔", "娜塔莎", "莫尼卡", "妮可", "摩根",
            "娜塔莉", "玛姬", "桑迪", "玛米", "莫甘娜", "埃莉诺", "莎拉", "拉克丝", "玛佩尔", "马德琳",
            "萨瓦娜", "赫芙拉", "瑞雪", "爱丝特", "波琳", "萨拉", "奈乐", "艾达", "阿德莱德", "约翰娜",
            "玛雅", "卡米拉", "坎蒂丝", "艾咪", "阿芙拉", "贝芙丽", "雷奥妮", "亚历克西斯", "爱丽丝",
            "阿比盖尔", "艾比", "阿娜丝塔", "妮莉雅", "安德莉亚", "安琪", "丽塔", "黛儿", "黛西",
            "露西，?", "丝塔茜", "索菲娅", "艾米瑞达", "艾玛", "玛乔丽?", "艾米丽", "琳", "玛佩尔?",
            "玛丽娅?", "克劳瑞丝", "保拉", "帕特丽夏", "米莉", "伊丽莎白", "弗雷德里卡", "维多利亚",
            "格拉蒂丝", "格罗瑞娅", "诺拉", "尤杜拉", "贾莎拉", "伊娃", "维尔莉特" };

    public static String randomName(List<String> names)
    {
        StringBuffer buffer = new StringBuffer();

        String name = names.get(RandomUtil.next(names.size()));
        buffer.append(name);

        buffer.append(familyNames[RandomUtil.next(familyNames.length)]);

        names.remove(new String(name));
        return buffer.toString();
    }

    public static String randomName(int sex)
    {
        StringBuffer buffer = new StringBuffer();
        String name = (sex == 1 ? nameMan[RandomUtil.next(nameMan.length)]
                : nameWoman[RandomUtil.next(nameWoman.length)]);
        buffer.append(name);
        buffer.append(familyNames[RandomUtil.next(familyNames.length)]);
        return buffer.toString();
    }

}
