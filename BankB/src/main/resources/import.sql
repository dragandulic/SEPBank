INSERT INTO `bankb`.`merchant` (`id`, `name`,`merchant_id`,`merchant_password`,`bankaccount_id`) VALUES ('1','Bankarstvo','411sldmcv81yt6j5nf9q2nv0m4u7v9','2',1);



INSERT INTO `bankb`.`merchant` (`id`, `name`,`merchant_id`,`merchant_password`,`bankaccount_id`) VALUES ('2','Zastita materijala','411BNG5dryh654f6j/9hrEEESX0','password@2',2);



INSERT INTO `bankb`.`bank_account` (`id`, `numberofaccount`,`sum`) VALUES ('1','411-4444444444444-44','40000');
INSERT INTO `bankb`.`bank_account` (`id`, `numberofaccount`,`sum`) VALUES ('2','411-4444444444444-55','50000');
INSERT INTO `bankb`.`bank_account` (`id`, `numberofaccount`,`sum`) VALUES ('3','411-4444444444444-66','30000');


INSERT INTO `bankb`.`card` (`id`, `pan`, `securitycode`, `cardholdername`, `expirationdate`, `bankaccount_id` ) VALUES ('1','4111 1111 1111 1111','233','Ilija Mijic','2019-02-20',3);