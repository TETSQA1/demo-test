*** Settings ***
Library    Collections
Resource   ../../resources/BackendUtils/auth.robot
Resource   ../../resources/BackendUtils/driver.robot
Resource   ../../resources/BackendUtils/counter.robot
Resource    ../../resources/BackendUtils/counter.robot
Resource    ../../resources/BackendUtils/recipient.robot

*** Test Cases ***
Verify the recipient collect the parcels successfully
    [Tags]    collect
    #login driver
    ${requestId_driver}=    Login As Driver   ${driver_phone_number}
    ${driver_token}=    Login As Driver Second Step    ${requestId_driver}    ${First_driver_otp}
    #create parcel
    ${parcel_id}=    Create parcel by driver using existing recipient   ${driver_token}
    #login counter
    ${requestId_counter}=    Login As Counter   ${counter_phone_number}
    ${counter_token}=    Login As Counter Second Step    ${requestId_counter}    ${First_counter_otp}
    #getting the parcel details using id API to check created status
    Get Parcel By Id    ${counter_token}    ${parcel_id}    ${driver_phone_number}    ${recipient_phone_number}    ${created_status}
    #validating the parcel
    Update Parcel Status By Counter    ${counter_token}    ${parcel_id}    ${driver_phone_number}    ${recipient_phone_number}
    #recipient login
    ${requestId}=    Login as Recipient first step   ${Recipient_Phone_number}
    ${recipient_token}=     Login As Recipient second step  ${requestId}    ${Recipient_verification_code}
    #getting the group id
    ${group_id}=    Get the group id    ${recipient_token}
    #changing the parcel status to collecting
    Change the status of the parcel into collecting    ${counter_token}    ${group_id}
    #Getting the parcel id from the group API
    Getting the parcel id using group    ${counter_token}    ${group_id}
    #changing the parcel status into collected
    Validating the parcel and changing the parcel status into collected    ${counter_token}    ${parcel_id}





