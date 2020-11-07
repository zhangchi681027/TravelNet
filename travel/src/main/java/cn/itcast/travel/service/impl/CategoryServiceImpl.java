package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService
{
    CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll()
    {
        Jedis jedis = JedisUtil.getJedis();

        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        List<Category> categories = null;

        if (category == null || category.size() == 0)
        {
            System.out.println("从数据库查询...");
            categories = categoryDao.findAll();
            for (Category cate : categories)
            {
                System.out.println(cate.getCname() + cate.getCid());
                jedis.zadd("category", cate.getCid(), cate.getCname());
            }
        }
        else
        {
            categories = new ArrayList<Category>();
            System.out.println("从redis查询...");
            int i = 0;
            for (Tuple s : category)
            {
                Category cs = new Category();
                cs.setCid((int) s.getScore());
                cs.setCname(s.getElement());
                categories.add(cs);
            }
        }

        return categories;
    }
}
