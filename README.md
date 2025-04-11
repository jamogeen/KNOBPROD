
# KNOB

# Introduction
 Knob is a middleware gateway for processing ISO8583 messages between payment systems. It's a 
 channel where different system communicate in a secure and convince way while maintaining a 
 low processing time. 
 
  The gateway is built using ISO8583 which is an ISO standard used for card processing systems
  around the world.

#### `Author james Gichiri`
#### `Date : 11/04/2025 ` 



# Sample ISO Messages fomrmats


 Pid: 16057160 Received At: 10/04/2025 15:09:43.230
MessageId: 1221
Field 002: 429934XXXXXX0865
Field 003: 010000
Field 004: 000000150000
Field 005: 000000150000
Field 006: 000000150000
Field 007: 2504101456
Field 009: 00000001
Field 010: 00000001
Field 011: 389137
Field 012: 250410145612
Field 014: 2809
Field 022: 510101513146
Field 024: 200
Field 025: 1506
Field 026: 6010
Field 028: 250410
Field 029:   2
Field 032: 440782
Field 037: 510011389137
Field 038: 868855
Field 039: 000
Field 041: POS16031
Field 042: POSBR000601
Field 043: KILIFI BRANCH POS MACHINE
Field 048: PO01T222 ROWN0101848389138ARWN0101848389137
Field 049: KES
Field 050: KES
Field 051: KES
Field 055: 5F3401019F02060000001500009F100706011203A0B8039F2608B60113EDF8531E4A9F360209089F3704E6B8873C
Field 095: 00030210865POSBR0006018688550005005SACCOROWN0101848389138
Field 098: 1848389138
Field 100: SACCO
Field 102: 01243020444201
Field 103: 01130590011108
Field 104: KILIFI BRANCH POS MACHINE POS16031 48400210865 012430204442010096
Field 123: POS
Field 127: AC7




Pid: 16057160 Received At: 10/04/2025 15:43:38.619
MessageId: 1100
Field 002: 440783XXXXXX2925
Field 003: 310000
Field 011: 389160
Field 012: 250410154337
Field 014: 2507
Field 022: M10101M13146
Field 024: 108
Field 026: 6010
Field 032: 440782
Field 035: 440783XXXXXXXXXXXX2925D25072211244299
Field 037: 510012389160
Field 038: 848977
Field 039: 000
Field 041: POS78673
Field 042: POSAG059570
Field 043: VICTOR MOSOMI
Field 048: PO99O222 ROWN0111848389160
Field 049: KES
Field 051: KES
Field 055: 9F26084D8C2DA27E7330059F2701809F100706011103A040009F4104000008999F370403255F109F36020075950500000000009A032504109C01009F02060000000000005F2A020404820200009F1A0204049F03060000000000009F3303E040C88407A000000003101
09F34033F00009F3501229F1E0834363830323031325F3401014F07A00000000310109F6E0420200000
Field 072: T01086AC101401125616881700ACT001CAM00010AM10010CY0003KESCY1003KESOTY00320aRTY00322aTYP00320a
Field 095: ROWN0101848389160
Field 098: 1848389160
Field 100: ISS2
Field 102: 01125616881700
Field 103: 01198213126400
Field 104: VICTOR MOSOMI POS78673 30128552925 01125616881700
Field 123: POS
Field 127: AC7


Pid: 16057160 Received At: 10/04/2025 15:57:11.097
MessageId: 1804
Field 011: 815846
Field 012: 250410155704
Field 024: 831
Field 123: ATM



# Sample JSON Requests:
## 1200

### Request
{
    
    "stan": "{{random_stan}}",
    "mti" : "1200",
    "fields": {
        "PAN": "4407830128994770",
        "ProcCode": "010000",
        "TranAmount": "000000006900",
        "BillingAmount": "000000006900",
        "TranDateTime": "2503271104",
        "ConvRateBilling": "00000001",
        "STAN": "{{random_stan}}",
        "LocalTime": "{{local_timestamp}}",
        "ExpiryDate": "2504",
        "POSEntryMode": "090010199100",
        "NII": "200",
        "POSCondCode": "1506",
        "POSPinCap": "6010",
        "TranFeeAmt": "250327",
        "SettleFeeAmt": "0",
        "AcqInstIdCode": "{{random_acq}}",
        "RetRefNo": "500722310483",
        "AuthIdResp": "659813",
        "TerminalId": "POS28548",
        "MerchantId": "POSAG030710",
        "CardAcceptorName": "Roselyne Njeri Gitau",
        "AddtlDataPrivate": "NO99O222 ROWN0103965418730",
        "CurrencyCode": "KES",
        "CurrencyCodeSettle": "KES",
        "CurrencyCodeCard": "KES",
        "DataRecord": "T01135AC101401116088023300AC2018011984032992000084ACT001CAM00046900AM10046900AM20046900CY0003KESCY1003KESCY2003KESOTY003207RTY003227TYP003207",
        "ReplacementAmounts": "00030214770POSAG030710659813ROWN0103965418730",
        "Payee": "3965418730",
        "ReceivingInstIdCode": "ISS2",
        "AccountId1": "01116088023300",
        "AccountId2": "01198403299200",
        "TranDesc": "Roselyne Njeri Gitau POS28548 30128994770 01116088023300",
        "ReservedPrivate4": "POS",
        "ReservedPrivate8": "AC7"
    }
}

### REsponse

{
    "mti": "1210",
    "status": "SUCCESS",
    "responseCode": "911",
    "fields": {
        "RespCode": "911",
        "FwdInstIdCode": "RTPSID",
        "CurrencyCodeSettle": "KES",
        "AuthIdResp": "659813",
        "AccountId2": "01198403299200",
        "AccountId1": "01116088023300",
        "CardAcceptorName": "Roselyne Njeri Gitau",
        "MerchantId": "POSAG030710    ",
        "TranFeeAmt": "250327",
        "CurrencyCode": "KES",
        "TranDateTime": "2503271104",
        "TerminalId": "POS28548",
        "ReceivingInstIdCode": "ISS2",
        "TranDesc": "Roselyne Njeri Gitau POS28548 30128994770 01116088023300",
        "ProcCode": "010000",
        "LocalTime": "250411150831",
        "ConvRateBilling": "00000001",
        "STAN": "974670",
        "RetRefNo": "500722310483",
        "AcqInstIdCode": "088628",
        "NII": "200",
        "CurrencyCodeCard": "KES",
        "ReservedPrivate4": "POS",
        "TranAmount": "000000006900",
        "ReplacementAmounts": "00030214770POSAG030710659813ROWN0103965418730",
        "PAN": "4407830128994770",
        "BillingAmount": "000000006900"
    }
}





