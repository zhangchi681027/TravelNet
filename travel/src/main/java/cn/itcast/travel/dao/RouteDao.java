package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao
{
    public int queryCountByCid(int cid);

    public List<Route> queryRouteList(int cid, int from, int pageSize);
}
