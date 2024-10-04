*** Settings ***
Library    Collections
Resource   ../../resources/BackendUtils/auth.robot
Resource   ../../resources/BackendUtils/driver.robot

*** Test Cases ***
Delivery Driver User Can Create Parcel Successfully
    [Tags]    Create_Parcel
    ${requestId}=    Login As Driver   ${driver_phone_number}
    Login As Driver With Email    ${First_driver_email}    ${requestId}
    ${driver_token}=    Login As Driver Second Step    ${requestId}    ${First_driver_otp}
    ${recipient_id}    ${recipient_name}    ${phone_number}=    Create Recipient By Driver    ${driver_token}
    Create Parcel By Driver using new recipient    ${driver_token}    ${recipient_id}    ${recipient_name}    ${phone_number}
