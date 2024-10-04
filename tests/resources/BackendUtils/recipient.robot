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

*** Variables ***

${create_parcel}        /v1/deliveryDriver/parcels
${group_id_endpoint}    /v1/recipient/parcels/counter/705f10b8-b754-4a59-be26-2c83261a096c/confirm/
${next_action_endpoint1}     /v1/counter/parcels/group/
${next_action_endpoint2}        /action
${get_by_group_endpoint}        /v1/counter/parcels/group/
${Validate_parcel_endpoint}     /v1/counter/parcels/
${get_by_EAN}          /v1/counter/parcels/ean/
${kanguroPointId}       0e9d1fbe-19ba-4d58-868b-ff4728327a72
${request_counter_to_confirm_endpoint1}      /v1/deliveryDriver/parcels/counter/
${request_counter_to_confirm_endpoint2}      /confirm-return
${Counter_id}       705f10b8-b754-4a59-be26-2c83261a096c
${pin}                  123456
${originalAddress}      Spain
${attachments}          []
${monies}               []
${adultSignatureRequired}      True
${recipientOnlyRequired}       True
${commercialInvoiceRequired}     False
${recipient_email_existing}      temp@gmail.com
${recipient_existing_name}      Helen
${recipient_existing_phone_number}      +34-636638194
${recipient_existing_id}        9ab63823-bfb1-4abb-9cac-22b5db186a46
${pin}      1234
${valid}        True
${action_collect}       deliver-to-recipient
${action_return}        collect-from-recipient
${action_return_driver}     deliver-to-delivery-driver

*** Keywords ***
Create parcel by driver using existing recipient
    [Arguments]       ${driver_token}
    ${new_ean}=    Generate Random EAN
    ${headers}=    Create Dictionary    Content-Type=application/json    Authorization=${driver_token}
    # Directly create the data dictionary
    ${parcel_data}=    Create Dictionary    EAN=${new_ean}    pin=${pin}    originalAddress=${originalAddress}    attachments=${attachments}    monies=${monies}    adultSignatureRequired=${adultSignatureRequired}    recipientOnlyRequired=${recipientOnlyRequired}    commercialInvoiceRequired=${commercialInvoiceRequired}
    ${data}=    Create Dictionary  recipientId=${recipient_existing_id}    recipientPhone=${recipient_existing_phone_number}    recipientEmail=${recipient_email_existing}    recipientName=${recipient_existing_name}    kanguroPointId=${kanguroPointId}    status=created        parcel=${parcel_data}
    # Send the request with the properly formatted JSON data
    ${response}=    Post   ${base_URL}${create_parcel}    headers=${headers}    json=${data}
    # Convert response content to JSON for verification
    ${response_json}=    Evaluate    json.loads($response.content)    json
    # Log the response body
    Log    Response Body: ${response.content}
    ${parcel_id}=    Set Variable    ${response_json['id']}
    Log    Created Parcel ID: ${parcel_id}
    # RETURN the parcel ID if needed
    RETURN    ${parcel_id}


Get the group id
    [Arguments]     ${recipient_token}
    ${headers}=    Create Dictionary    Content-Type=application/json    Authorization=${recipient_token}
    ${response}=        PATCH   ${base_URL}${group_id_endpoint}   headers=${headers}
    #${response_json}=    Evaluate    json.loads($response.content)    json
    # Log the response content before parsing
    Log     Response Body: ${response.content}
    # Correct usage of Evaluate to parse JSON response
    ${response_json}=    Evaluate    json.loads('''${response.content}''')
    # Check if 'id' exists before trying to access it
    Run Keyword If    'groupId' not in ${response_json}    Fail    Key 'groupId' not found in response
    # Access the 'id' key
    ${group_id}=    Set Variable    ${response_json['groupId']}
    # Log the group ID
    Log    group ID: ${group_id}
    RETURN    ${group_id}

Change the status of the parcel into collecting
    [Arguments]     ${Counter_token}    ${group_id}
    ${headers}=     Create Dictionary       content-Type=application/json       Authorization=${Counter_Token}
    ${response}=        GET     ${base_URL}${next_action_endpoint1}${group_id}${next_action_endpoint2}      headers=${headers}
    ${response_json}=    Evaluate    json.loads($response.content)    json
    Log    Response Body: ${response.content}

Getting the parcel id using group
    [Arguments]     ${Counter_token}    ${group_id}
    ${headers}=     Create Dictionary       content-Type=application/json       Authorization=${Counter_Token}
    ${response}=        GET     ${base_URL}${get_by_group_endpoint}${group_id}      headers=${headers}
    Log     Response Body: ${response.content}
    ${response_json}=    Evaluate    json.loads('''${response.content}''')
    ${parcel_id}=    Set Variable    ${response_json['items'][0]['id']}
    Log    Parcel ID: ${parcel_id}
    RETURN    ${parcel_id}

Validating the parcel and changing the parcel status into collected
    [Arguments]    ${counter_token}   ${parcel_id}
    ${headers}=    Create Dictionary    Content-Type=application/json    Authorization=${counter_token}
    ${data} =   Create Dictionary   action=${action_collect}    valid=${valid}     pin=${pin}  attachments=${attachments}   monies=${monies}    adultSignatureRequired=${adultSignatureRequired}    recipientOnlyRequired=${recipientOnlyRequired}    commercialInvoiceRequired=${commercialInvoiceRequired}
    ${response}=    PATCH    ${base_URL}${Validate_parcel_endpoint}${parcel_id}    headers=${headers}   json=${data}
    Log    Response Body: ${response.content}
    Should Be Equal As Strings    ${response.status_code}    204

Create a return parcel
    [Arguments]       ${driver_token}
    ${new_ean}=    Generate Random EAN
    ${headers}=    Create Dictionary    Content-Type=application/json    Authorization=${driver_token}
    # Directly create the data dictionary
    ${parcel_data}=    Create Dictionary    EAN=${new_ean}    pin=${pin}    originalAddress=${originalAddress}    attachments=${attachments}    monies=${monies}    adultSignatureRequired=${adultSignatureRequired}    recipientOnlyRequired=${recipientOnlyRequired}    commercialInvoiceRequired=${commercialInvoiceRequired}
    ${data}=    Create Dictionary    recipientId=${recipient_existing_id}    recipientPhone=${recipient_existing_phone_number}    recipientEmail=${recipient_email_existing}    recipientName=${recipient_existing_name}    kanguroPointId=${kanguroPointId}    status=created-recipient        parcel=${parcel_data}
    # Send the request with the properly formatted JSON data
    ${response}=    Post   ${base_URL}${create_parcel}    headers=${headers}    json=${data}
    # Convert response content to JSON for verification
    ${response_json}=    Evaluate    json.loads($response.content)    json
    # Log the response body
    Log    Response Body: ${response.content}
    ${EAN}=    Set Variable    ${response_json['EAN']}
    Log    Created Parcel EAN: ${EAN}
    # RETURN the parcel ID if needed
    RETURN    ${EAN}

Get parcel id by EAN
    [Arguments]       ${counter_token}    ${EAN}
    ${headers}=    Create Dictionary    Content-Type=application/json    Authorization=${counter_token}
    ${response}=    get   ${base_URL}${get_by_EAN}${EAN}    headers=${headers}
    ${response_json}=    Evaluate    json.loads($response.content)    json
    # Log the response body
    Log    Response Body: ${response.content}
    # Save the ID of the created parcel
    ${parcel_id}=    Set Variable    ${response_json['id']}
    Log    Created Parcel ID: ${parcel_id}
    # RETURN the parcel ID if needed
    RETURN    ${parcel_id}


Validating the parcel and changing the parcel status into drop-off recipient
    [Arguments]    ${counter_token}   ${parcel_id}
    ${headers}=    Create Dictionary    Content-Type=application/json    Authorization=${counter_token}
    ${data} =   Create Dictionary   action=${action_return}    valid=${valid}     pin=${pin}  attachments=${attachments}   monies=${monies}    adultSignatureRequired=${adultSignatureRequired}    recipientOnlyRequired=${recipientOnlyRequired}    commercialInvoiceRequired=${commercialInvoiceRequired}
    ${response}=    PATCH    ${base_URL}${Validate_parcel_endpoint}${parcel_id}    headers=${headers}   json=${data}
    Log    Response Body: ${response.content}
    Should Be Equal As Strings    ${response.status_code}    204

Confirm the returning of drop-off recipient parcel
    [Arguments]    ${Driver_token}
    ${headers}=    Create Dictionary    Content-Type=application/json    Authorization=${Driver_token}
    ${response}=    PATCH    ${base_URL}${request_counter_to_confirm_endpoint1}${Counter_id}${request_counter_to_confirm_endpoint2}   headers=${headers}
    ${response_json}=    Evaluate    json.loads($response.content)    json
    Log    Response Body: ${response.content}
    ${group_id}=    Set Variable    ${response_json['groupId']}
    Log    group ID: ${group_id}
    RETURN    ${group_id}

Updating the parcel status into returning
    [Arguments]     ${Counter_token}    ${group_id}
    ${headers}=     Create Dictionary       content-Type=application/json       Authorization=${Counter_Token}
    ${response}=        GET     ${base_URL}${next_action_endpoint1}${group_id}${next_action_endpoint2}      headers=${headers}
    ${response_json}=    Evaluate    json.loads($response.content)    json
    Log    Response Body: ${response.content}


Validating the return parcel and changing the status to returned
    [Arguments]    ${counter_token}   ${parcel_id}
    ${headers}=    Create Dictionary    Content-Type=application/json    Authorization=${counter_token}
    ${data} =   Create Dictionary   action=${action_return_driver}    valid=${valid}     pin=${pin}  attachments=${attachments}   monies=${monies}    adultSignatureRequired=${adultSignatureRequired}    recipientOnlyRequired=${recipientOnlyRequired}    commercialInvoiceRequired=${commercialInvoiceRequired}
    ${response}=    PATCH    ${base_URL}${Validate_parcel_endpoint}${parcel_id}    headers=${headers}   json=${data}
    Log    Response Body: ${response.content}
    Should Be Equal As Strings    ${response.status_code}    204













