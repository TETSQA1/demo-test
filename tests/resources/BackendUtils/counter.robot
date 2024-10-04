*** Settings ***
Library    RequestsLibrary
Library    Collections
Library    json
Library    String
Library    DateTime
Library    OperatingSystem
Library    BuiltIn
Library    Process
Resource    auth.robot
Resource    counter.robot
Resource    driver.robot

*** Variables ***

${get_parcel_by_id}     /v1/counter/parcels

*** Keywords ***
Get Parcel By Id
    [Arguments]       ${counter_token}    ${parcel_id}    ${driver_phone_number}    ${recipient_phone_number}    ${status}
    ${headers}=    Create Dictionary    Content-Type=application/json    Authorization=${counter_token}    
    ${response}=    get   ${base_URL}${get_parcel_by_id}/${parcel_id}    headers=${headers}
    ${response_json}=    Evaluate    json.loads($response.content)    json
    Log    Response Body: ${response.content}
    # Asserting required fields in the response
    Should Be True    'id' in ${response_json}    Parcel ID is missing in the response
    Should Be True    'status' in ${response_json}    Status is missing in the response
    Should Be True    'originalAddress' in ${response_json}    Original Address is missing in the response
    Should Be True    'courier' in ${response_json}    Courier information is missing in the response
    Should Be True    'deliveryDriver' in ${response_json}    Delivery Driver information is missing in the response


    # Asserting specific values
    Should Be Equal    ${response_json['status']}    ${status}    Parcel status is not 'created'
    Should Be Equal    ${response_json['originalAddress']}    Spain    Original address is not 'Spain'
    Should Be Equal    ${response_json['courier']['name']}    DHL Courier    Courier name is not 'DHL Courier'

    # Asserting delivery driver details
    Should Be Equal    ${response_json['deliveryDriver']['profile']['name']}    Nicolas    Driver name is not 'Nicolas'
    Should Be Equal    ${response_json['deliveryDriver']['profile']['phone']}    ${driver_phone_number}    Driver phone is not '+34-254789686'

    # Asserting kanguro point details
    Should Be Equal    ${response_json['kanguroPoint']['addressComponents']['city']}    Spain    City in kanguroPoint is not 'Spain'

    # Asserting recipient details
    Should Be Equal    ${response_json['recipient']['profile']['phone']}    ${recipient_phone_number}    Recipient phone is not matching the provided phone number

    # Asserting non-nullable fields (Should Not Be None)
    Should Not Be Equal    ${response_json['id']}    None    Parcel ID is null
    Should Not Be Equal    ${response_json['createdAt']}    None    Creation timestamp is null
    Should Not Be Equal    ${response_json['courier']['id']}    None    Courier ID is null

Update Parcel Status By Counter
    [Arguments]    ${counter_token}    ${parcel_id}    ${driver_phone_number}    ${recipient_phone_number}
    ${data}=    Create Dictionary    valid=True       action=collect-from-delivery-driver    monies=[]    pin=123456    adultSignature=True    recipientOnly=True    commercialInvoice=True    title=not an incidence    description=testing valid drop-off    attachments=[]
    ${headers}=    Create Dictionary    Content-Type=application/json    Authorization=${counter_token}    
    ${response}=    patch    ${base_URL}${get_parcel_by_id}/${parcel_id}    headers=${headers}    json=${data}
    Log    Response Body: ${response.content}


