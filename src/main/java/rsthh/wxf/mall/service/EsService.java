package rsthh.wxf.mall.service;

import rsthh.wxf.mall.po.Item;

import java.io.IOException;

public interface EsService {
    public void agreement(Item item) throws IOException;

    public void agreement2(String item) throws IOException;
}
