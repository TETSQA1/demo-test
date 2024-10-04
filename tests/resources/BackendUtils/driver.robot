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

${create_recipient}     /v1/deliveryDriver/parcels/recipients
${create_parcel}        /v1/deliveryDriver/parcels
${kanguroPointId}       0e9d1fbe-19ba-4d58-868b-ff4728327a72
${pin}                  123456
${originalAddress}      Spain
${attachments}          []
${monies}               []
${adultSignatureRequired}      True
${recipientOnlyRequired}       True
${commercialInvoiceRequired}     False

*** Keywords ***
Create Recipient By Driver
    [Arguments]       ${driver_token}
    ${recipient_name}=    Generate Random Name
    ${recipient_phone_number}=    Generate Random Phone Number
    ${headers}=    Create Dictionary    Content-Type=application/json    Authorization=${driver_token}    
    ${data}=    Create Dictionary    phone=${recipient_phone_number}    name=${recipient_name}
    ${response}=    Post   ${base_URL}${create_recipient}    headers=${headers}    json=${data}
    ${response_json}=    Evaluate    json.loads($response.content)    json
    Log    Response Body: ${response.content}
    # Assert that the response status code is 200
    Should Be Equal As Strings    ${response.status_code}    200
    # Assertions for the response properties
    Should Not Be Empty    ${response_json['id']}
    Should Not Be Empty    ${response_json['profile']['id']}
    Should Be Equal As Strings    ${response_json['profile']['name']}    ${recipient_name}
    Should Be Equal As Strings    ${response_json['profile']['phone']}    ${recipient_phone_number}
    Should Be Equal As Strings    ${response_json['profile']['multipleCounters']}    False
    Should Be Equal As Strings    ${response_json['profile']['signaturePointsProvided']}    False
    # Save the first ID in a variable
    ${recipient_id}=    Set Variable    ${response_json['id']}
    # Log the saved ID
    Log    Recipient ID: ${recipient_id}   
    RETURN    ${recipient_id}    ${recipient_name}    ${recipient_phone_number}
Create Parcel By Driver using new recipient
    [Arguments]       ${driver_token}    ${recipient_id}    ${recipient_name}    ${phone_number}
    ${new_ean}=    Generate Random EAN
    ${recipient_email}=    Generate Random Email
    ${headers}=    Create Dictionary    Content-Type=application/json    Authorization=${driver_token}
    # Directly create the data dictionary
    ${parcel_data}=    Create Dictionary    EAN=${new_ean}    pin=${pin}    originalAddress=${originalAddress}    attachments=${attachments}    monies=${monies}    adultSignatureRequired=${adultSignatureRequired}    recipientOnlyRequired=${recipientOnlyRequired}    commercialInvoiceRequired=${commercialInvoiceRequired}
    ${data}=    Create Dictionary    recipientId=${recipient_id}    recipientPhone=${phone_number}    recipientEmail=${recipient_email}    recipientName=${recipient_name}    kanguroPointId=${kanguroPointId}    status=created    parcel=${parcel_data}
    # Send the request with the properly formatted JSON data
    ${response}=    Post   ${base_URL}${create_parcel}    headers=${headers}    json=${data}
    # Convert response content to JSON for verification
    ${response_json}=    Evaluate    json.loads($response.content)    json
    # Log the response body
    Log    Response Body: ${response.content}
    # Assertions
    Should Be Equal As Strings    ${response_json['status']}    created
    Should Be Equal As Strings   ${response_json['pinRequired']}    True
    Should Be Equal As Strings   ${response_json['adultSignatureRequired']}    False
    Should Be Equal As Strings   ${response_json['recipientOnlyRequired']}    False
    Should Be Equal As Strings   ${response_json['commercialInvoiceRequired']}    False
    Should Be Equal As Strings              ${response_json['expireTimestamp']}   ${null}
    Should Be Empty               ${response_json['attachments']}
    Should Be Empty               ${response_json['monies']}
    # Save the ID of the created parcel
    ${parcel_id}=    Set Variable    ${response_json['id']}
    Log    Created Parcel ID: ${parcel_id}
    # RETURN the parcel ID if needed
    RETURN    ${parcel_id}

