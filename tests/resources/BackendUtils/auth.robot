*** Settings ***
Library       RequestsLibrary
Library    Collections
Library    json
Library    String
Library    DateTime
Library    OperatingSystem
Library    BuiltIn
Library    Process

*** Variables ***
#Environment URLs
${base_URL}                               https://api.test.kanguro.com/api
${counter_login_first_step}                    /v1/counter/auth/login-first-step
${driver_login_first_step}                    /v1/deliveryDriver/auth/login-first-step
${counter_login_email_first_step}            /v1/counter/auth/login-first-step-email
${driver_login_email_first_step}            /v1/deliveryDriver/auth/login-first-step-email
${counter_login_second_step}               /v1/counter/auth/login-second-step
${driver_login_second_step}               /v1/deliveryDriver/auth/login-second-step
${counter_phone_number}                   +34-455879658
${driver_phone_number}                    +34-254789686
${First_counter_email}
${First_driver_email}
${First_counter_otp}                      1234
${First_driver_otp}                       1234
${COUNTRY_CODE}                           +34
${PREFIX}                                  34
${created_status}                           created
${dropped_off_status}                        dropped-off
${Recipient_login_first_step_endpoint}     /v1/recipient/auth/login-first-step
${Recipient_login_Second_step_endpoint}    /v1/recipient/auth/login-second-step
${Recipient_profile_end_point}      /v1/recipient/profile/me
${Recipient_Phone_number}           +34-636638194
${Recipient_verification_code}      1234

*** Keywords ***
Login As Counter
    [Arguments]    ${phone_number}
    ${headers}=   Create Directory    Content-Type=application/json
    ${data}=    Create Dictionary    phone=${counter_phone_number}
    ${response}=    POST       ${base_URL}${counter_login_first_step}    headers=${HEADERS}    json=${data}
    ${response_json}=        Evaluate    json.loads($response.content)    json
    Log    Response Body: ${response.content}
    Should Be Equal As Strings    ${response.status_code}    200
    ${response_dict}=    Evaluate    json.loads($response.content)    json
    ${requestId}=    Get From Dictionary    ${response_dict}  requestId  default=None 
    Log    requestId: ${requestId}
    Should Not Be Empty    ${requestId}
    RETURN     ${requestId}
Login As Counter With Email
    [Arguments]    ${email}    ${requestId}
    ${headers}=   Create Directory    Content-Type=application/json
    ${data}=    Create Dictionary    email=${First_counter_email}    requestId=${requestId}
    ${response}=    POST     ${base_URL}${counter_login_email_first_step}    headers=${HEADERS}    json=${data}
    ${response_json}=        Evaluate    json.loads($response.content)    json
    Log    Response Body: ${response.content}
    Should Be Equal As Strings    ${response.status_code}    200
    ${response_dict}=    Evaluate    json.loads($response.content)    json
    ${requestId}=    Get From Dictionary    ${response_dict}  requestId  default=None 
    Log    requestId: ${requestId}
    Should Not Be Empty    ${requestId}
Login As Counter Second Step
    [Arguments]    ${requestId}    ${verificationCode}
    ${headers}=   Create Directory    Content-Type=application/json
    ${data}=    Create Dictionary    requestId=${requestId}    verificationCode=${verificationCode}
    ${response}=    post     ${base_URL}${counter_login_second_step}    headers=${HEADERS}    json=${data}
    ${response_json}=        Evaluate    json.loads($response.content)    json
    Log    Response Body: ${response.content}
    Should Be Equal As Strings    ${response.status_code}    200
    ${response_dict}=    Evaluate    json.loads($response.content)    json
    ${accessToken}=    Get From Dictionary    ${response_dict}  accessToken  default=None 
    ${counter_token}=    Set Variable    Bearer ${accessToken}
    Log    accessToken: ${accessToken}
    Log    counter_token: ${counter_token}
    Should Not Be Empty    ${accessToken}
    RETURN     ${counter_token}
Login As Driver
    [Arguments]    ${phone_number}
    ${headers}=   Create Directory    Content-Type=application/json
    ${data}=    Create Dictionary    phone=${driver_phone_number}
    ${response}=    post     ${base_URL}${driver_login_first_step}    headers=${HEADERS}    json=${data}
    ${response_json}=        Evaluate    json.loads($response.content)    json
    Log    Response Body: ${response.content}
    Should Be Equal As Strings    ${response.status_code}    200
    ${response_dict}=    Evaluate    json.loads($response.content)    json
    ${requestId}=    Get From Dictionary    ${response_dict}  requestId  default=None 
    Log    requestId: ${requestId}
    Should Not Be Empty    ${requestId}
    RETURN     ${requestId}
Login As Driver With Email
    [Arguments]    ${email}    ${requestId}
    ${headers}=   Create Directory    Content-Type=application/json
    ${data}=    Create Dictionary    email=${First_driver_email}    requestId=${requestId}
    ${response}=    post     ${base_URL}${driver_login_email_first_step}    headers=${HEADERS}    json=${data}
    ${response_json}=        Evaluate    json.loads($response.content)    json
    Log    Response Body: ${response.content}
    Should Be Equal As Strings    ${response.status_code}    200
    ${response_dict}=    Evaluate    json.loads($response.content)    json
    ${requestId}=    Get From Dictionary    ${response_dict}  requestId  default=None 
    Log    requestId: ${requestId}
    Should Not Be Empty    ${requestId}
Login As Driver Second Step
    [Arguments]    ${requestId}    ${verificationCode}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${data}=    Create Dictionary    requestId=${requestId}    verificationCode=${verificationCode}
    ${response}=    Post   ${base_URL}${driver_login_second_step}    headers=${headers}    json=${data}
    ${response_json}=    Evaluate    json.loads($response.content)    json
    Log    Response Body: ${response.content}
    Should Be Equal As Strings    ${response.status_code}    200
    ${response_dict}=    Evaluate    json.loads($response.content)    json
    ${accessToken}=    Get From Dictionary    ${response_dict}    accessToken    default=None
    ${driver_token}=    Set Variable    Bearer ${accessToken}
    Log    accessToken: ${accessToken}
    Log    driver_token: ${driver_token}
    Should Not Be Empty    ${accessToken}
    RETURN    ${driver_token}

Login as Recipient first step
    [Arguments]    ${phone_number}
    ${headers}=   Create Dictionary    Content-Type=application/json
    ${data}=    Create Dictionary    phone=${Recipient_phone_number}
    ${response}=    post     ${base_URL}${Recipient_login_first_step_endpoint}    headers=${HEADERS}    json=${data}
    ${response_json}=        Evaluate    json.loads($response.content)    json
    Log    Response Body: ${response.content}
    Should Be Equal As Strings    ${response.status_code}    200
    ${response_dict}=    Evaluate    json.loads($response.content)    json
    ${requestId}=    Get From Dictionary    ${response_dict}  requestId  default=None
    Log    requestId: ${requestId}
    Should Not Be Empty    ${requestId}
    RETURN     ${requestId}

Login As Recipient second step
    [Arguments]    ${requestId}    ${verificationCode}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${data}=    Create Dictionary    requestId=${requestId}    verificationCode=${verificationCode}
    ${response}=    Post   ${base_URL}${Recipient_login_Second_step_endpoint}    headers=${headers}    json=${data}
    ${response_json}=    Evaluate    json.loads($response.content)    json
    Log    Response Body: ${response.content}
    Should Be Equal As Strings    ${response.status_code}    200
    ${response_dict}=    Evaluate    json.loads($response.content)    json
    ${accessToken}=    Get From Dictionary    ${response_dict}    accessToken    default=None
    ${Recipient_token}=    Set Variable    Bearer ${accessToken}
    Log    accessToken: ${accessToken}
    Log    Recipient_token: ${Recipient_token}
    Should Not Be Empty    ${accessToken}
    RETURN    ${Recipient_token}


Generate Random Name
    ${random_name}=    Generate Random String    8    [lowercase]
    ${random_name}=    Replace String    ${random_name}    [    ${EMPTY}
    ${random_name}=    Replace String    ${random_name}    ]    ${EMPTY}
    RETURN    ${random_name}
Generate Random Phone Number
    ${prefix}=    Set Variable    +963-
    ${random_digits}=    Generate Random String    8    0123456789
    ${phone_number}=    Catenate    SEPARATOR=    ${prefix}    ${random_digits}
    RETURN    ${phone_number}
Generate Random EAN
    ${prefix}=    Set Variable    599
    ${random_digits}=    Generate Random String    9    0123456789
    ${ean}=    Catenate    SEPARATOR=     ${prefix}    ${random_digits}
    RETURN    ${ean}
Generate Random Email
    [Arguments]    ${length}=4
    ${random_string}=    Generate Random String    ${length}    [LETTERS] [NUMBERS]
    ${random_number}=    Generate Random String    ${length}    [NUMBERS]
    RETURN    auto${random_string}@auto${random_number}.automation