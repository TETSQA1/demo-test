*** Settings ***
Library    Collections
Resource   ../../resources/BackendUtils/auth.robot
Resource   ../../resources/BackendUtils/driver.robot
Resource   ../../resources/BackendUtils/counter.robot
Resource    ../../resources/BackendUtils/counter.robot
Resource    ../../resources/BackendUtils/recipient.robot

*** Test Cases ***
Verify the recipient can return the parcels successfully
    [Tags]    return
    #login driver
    ${requestId_driver}=    Login As Driver   ${driver_phone_number}
    ${driver_token}=    Login As Driver Second Step    ${requestId_driver}    ${First_driver_otp}
    #creating a return parcel
    ${EAN}=    Create a return parcel   ${driver_token}
    #login counter
    ${requestId_counter}=    Login As Counter   ${counter_phone_number}
    ${counter_token}=    Login As Counter Second Step    ${requestId_counter}    ${First_counter_otp}
    #getting the parcel id by EAN
    ${parcel_id}=       Get parcel id by EAN    ${counter_token}    ${EAN}
    #validating the parcel
    Validating the parcel and changing the parcel status into drop-off recipient    ${counter_token}    ${parcel_id}
    #getting the group id by confirming with the counter
    ${group_id}=        Confirm the returning of drop-off recipient parcel    ${driver_token}
    #changing the parcel status to returning
    Updating the parcel status into returning    ${counter_token}    ${group_id}
    #getting the parcel id using group id
    Getting the parcel id using group    ${counter_token}    ${group_id}
    #validating the parcel and change parcel status to returned
    Validating the return parcel and changing the status to returned    ${counter_token}    ${parcel_id}



