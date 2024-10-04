*** Settings ***
Library    Collections
Resource   ../../resources/BackendUtils/auth.robot
Resource   ../../resources/BackendUtils/driver.robot
Resource   ../../resources/BackendUtils/counter.robot
Resource    ../../resources/BackendUtils/counter.robot
Resource    ../../resources/BackendUtils/recipient.robot

*** Test Cases ***
Counter User Can Receive Parcel From Delivery Driver Successfully
    [Tags]    drop_off
    ${requestId_driver}=    Login As Driver   ${driver_phone_number}
    Login As Driver With Email    ${First_driver_email}    ${requestId_driver}
    ${driver_token}=    Login As Driver Second Step    ${requestId_driver}    ${First_driver_otp}
    ${recipient_id}    ${recipient_name}    ${recipient_phone_number}=    Create Recipient By Driver    ${driver_token}
    ${parcel_id}=    Create Parcel By Driver using new recipient    ${driver_token}    ${recipient_id}    ${recipient_name}    ${recipient_phone_number}
    ${requestId_counter}=    Login As Counter   ${counter_phone_number}
    #Login counter
    Login As Counter With Email    ${First_counter_email}    ${requestId_counter}
    ${counter_token}=    Login As Counter Second Step    ${requestId_counter}    ${First_counter_otp}
    #getting the parcels by id
    Get Parcel By Id    ${counter_token}    ${parcel_id}    ${driver_phone_number}    ${recipient_phone_number}    ${created_status}
    Update Parcel Status By Counter    ${counter_token}    ${parcel_id}    ${driver_phone_number}    ${recipient_phone_number}
    Get Parcel By Id    ${counter_token}    ${parcel_id}    ${driver_phone_number}    ${recipient_phone_number}    ${dropped_off_status}
