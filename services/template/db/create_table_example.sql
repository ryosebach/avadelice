DROP TABLE IF EXISTS `example`;
CREATE TABLE `example` (
  `id` bigint(20) unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `example_key` varchar(50) UNIQUE NOT NULL COMMENT 'key',
  `name_ja` varchar(191) NOT NULL COMMENT '名前(日本語)',
  `name_en` varchar(191) NOT NULL COMMENT '名前(英語)',
  `name_zh` varchar(191) NOT NULL COMMENT '名前(中国語)',
  `name_ko` varchar(191) NOT NULL COMMENT '名前(韓国語)',
  `enabled` tinyint(4) NULL COMMENT '有効フラグ',
  `created_at` datetime NOT NULL COMMENT '作成日時',
  `updated_at` datetime NOT NULL COMMENT '更新日時'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
COMMENT 'exampleテーブル';
