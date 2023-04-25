package com.g1app.engine.userdirectory.request;

public class RequestCrmData {
    int Created_by;
    String a_address_line_1;
    String a_address_line_2;
    String a_geo_address;
    double a_geo_latitude;
    double a_geo_longitude;
    String a_landmark;
    int a_pincode;
    String ca_alt_email;
    String ca_alternate_number;
    String ca_email;
    String ca_fname;
    String ca_gender;
    int ca_id;
    String ca_dob;
    String ca_isAffilatedAccount;
    String ca_mob;
    String ca_sname;
    String cities_city_id;
    int cm_id;
    String created_date;
    int o_amt_recd;
    String o_date;
    int o_discount;
    int o_id;
    int o_net_payable;
    String o_number;
    int o_status;
    String o_time;
    PaymentInfo paymentInfo;
    PackageInfo packageInfo;
    int updated_by;
    String updated_date;
}

    class PaymentInfo {
        int pay_amount;
        String pay_date;
        int pay_methord;
        int payment_recieved_By;
        String payment_remark;
        String txnid;
    }

    class PackageInfo {
        int ot_discount_amount;
        int ot_final_amt;
        int ot_price;
        String pac_test_type;
        int package_pkg_id;
        String promos_p_id;
    }
