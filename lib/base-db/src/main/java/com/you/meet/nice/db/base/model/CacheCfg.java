package com.you.meet.nice.db.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cache_cfg")
public class CacheCfg {
    /**
     * 主键，配置key
     */
    @Id
    @Column(name = "`key`")
    private String key;

    /**
     * 配置值
     */
    @Column(name = "`value`")
    private String value;

    /**
     * 配置描述
     */
    @Column(name = "`desc`")
    private String desc;
}