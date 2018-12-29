package com.snailmann.seckill.controller;

import com.snailmann.seckill.entity.BusinessOrder;
import com.snailmann.seckill.entity.User;
import com.snailmann.seckill.entity.dto.GoodsDto;
import com.snailmann.seckill.constant.CodeMsg;
import com.snailmann.seckill.rabbitmq.MsgSender;
import com.snailmann.seckill.rabbitmq.bean.SeckillMessage;
import com.snailmann.seckill.redis.service.RedisHandler;
import com.snailmann.seckill.redis.template.key.Goodskey;
import com.snailmann.seckill.service.GoodsService;
import com.snailmann.seckill.service.OrderService;
import com.snailmann.seckill.service.SeckillService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 秒杀控制层
 * @author liwenjie
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    SeckillService seckillService;
    @Autowired
    RedisHandler redisHandler;
    @Autowired
    MsgSender msgSender;

    /**
     * 根据商品Id对某商品进行秒杀操作
     *
     * @param user 根据token获取用户信息
     * @param model
     * @param goodsId 商品id
     * @return
     */
    @RequestMapping("/do")
    public String doSeckill(User user, Model model, @RequestParam String goodsId) {
        model.addAttribute("user", user);
        //判断用户是否是空，如果是空，则返回登录页面
        if (user == null){
            return "login";
        }

        //1. 系统初始化，把商品库存数据加载到Redis - afterPropertiesSet()方法
        //2. 收到请求，Redis预减库存，库存不足，直接返回秒杀失败，否则进入3
        Integer count = redisHandler.get(Goodskey.getSeckillGoodsStockCount,goodsId,Integer.class);
        if (count < 0){
            model.addAttribute("errmsg", CodeMsg.SECKILL_OVER.getMsg());
            return "seckill-fail";
        }
        //判断已经有秒杀订单
        BusinessOrder businessOrder = orderService.getSeckillOrder(goodsId,user.getId());
        if (businessOrder != null){
            model.addAttribute("errmsg",CodeMsg.REPEATE_SECKILL.getMsg());
            return "seckill-fail";
        }
        //3. 请求入队，立即返回排队中
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setUser(user);
        seckillMessage.setGoodsId(Long.valueOf(goodsId));
        msgSender.sendSeckillMsg(seckillMessage);
        model.addAttribute("successmsg","排队中");
        //入队成功，返回排队中
        return "seckill-wait";
        //4. 请求出队，生成订单，减少库存
        //5. 客户端轮询，是否秒杀成功
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsDto> goodsDtoList = goodsService.listGoodsDto();
        if (goodsDtoList == null){
            return;
        }
        goodsDtoList.forEach(goodsDto -> {
            //将商品的库存插入到缓存中,key为goods-count-goodsId
            redisHandler.set(Goodskey.getSeckillGoodsStockCount,""+goodsDto.getId(),goodsDto.getStockCount());
        });

    }
}
