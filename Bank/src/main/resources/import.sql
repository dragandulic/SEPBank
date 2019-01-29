INSERT INTO `bank`.`merchant` (`id`, `name`,`merchant_id`,`merchant_password`,`bankaccount_id`) VALUES ('1','Matematicki vesnik','977dk4mdjw3bz82hd71abgm39dm48f','1','1');
INSERT INTO `bank`.`merchant` (`id`, `name`,`merchant_id`,`merchant_password`,`bankaccount_id`) VALUES ('2','Saskin magazin','411sldmcv81yt6j5nf9q2nv0m4u7v9','2','2');


INSERT INTO `bank`.`bank_account` (`id`, `numberofaccount`,`sum`) VALUES ('1','111-1111111111111-11','10000');
INSERT INTO `bank`.`bank_account` (`id`, `numberofaccount`,`sum`) VALUES ('2','111-2222222222222-22','20000');
INSERT INTO `bank`.`bank_account` (`id`, `numberofaccount`,`sum`) VALUES ('3','111-3333333333333-33','30000');
INSERT INTO `bank`.`bank_account` (`id`, `numberofaccount`,`sum`) VALUES ('4','222-4444444444444-44','40000');

INSERT INTO `bank`.`card` (`id`, `pan`, `securitycode`, `cardholdername`, `expirationdate`, `bankaccount_id` ) VALUES ('1','1111 1111 1111 1111','234','Maja Majic','2019-02-19',3);
