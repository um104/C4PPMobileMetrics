# Credential fields. Personalize these.
USERNAME="c4@capstone.com"
PASSWORD="escape76"
SECURITYTOKEN="FVp2n6LFH2kPrAXfrUMzinne"
CLIENTID="3MVG9y6x0357HlecylRTsJx8y_qIjGh9Z7CQEA0bTx5xHsmQRBBXZaOldH3._q.NTUYlX1A4JdiewYx5qMvU4"
CLIENTSECRET="603269615811711635"

# Function for parsing the Instance URL and Access Token, courtesy of
# https://gist.github.com/cjus/1047794
function jsonval {
    temp=$(echo $RESPONSE | sed 's/\\\\\//\//g' | sed 's/[{}]//g' | awk -v k="text" '{n=split($0,a,","); for (i=1; i<=n; i++) print a[i]}' | sed 's/\"\:\"/\|/g' | sed 's/[\,]/ /g' | sed 's/\"//g' | grep -w $PROP| sed -e 's/^ *//g' -e 's/ *$//g')
    echo ${temp##*|}
}

echo
echo 'Be sure to change the username, password, security token, client ID, and client secret before running!'
echo 'See https://na11.salesforce.com/help/doc/en/user_security_token.htm for more info on the security token.'
echo 'See http://www.salesforce.com/us/developer/docs/api_rest/Content/quickstart_oauth.htm for info on the clientId and client secret.'
echo

# Attempt to authenticate
RESPONSE=`curl --silent -S https://login.salesforce.com/services/oauth2/token -d "grant_type=password" -d "client_id=$CLIENTID" -d "client_secret=$CLIENTSECRET" -d "username=$USERNAME" -d "password=$PASSWORD$SECURITYTOKEN"`

# check for errors
PROP="error"
ERROR=$(jsonval)
if [ "$ERROR" != "" ]; then
    PROP="error_description"
    ERROR_DESCRIPTION=$(jsonval)
    echo "AUTHENTICATION ERROR ENCOUNTERED"
    echo "ERROR: " $ERROR
    echo "ERROR_DESCRIPTION: " $ERROR_DESCRIPTION; echo
    echo "FULL ERROR MESSAGE: " $RESPONSE; echo
    echo "Your credentials may be invalid -- check them again!"; echo
    exit
else
    echo "AUTHENTICATION SUCCESSFUL"; echo
fi


# Retrieve the Access Token
PROP="access_token"
ACCESS_TOKEN=$(jsonval)

# Retrieve the Instance URL
PROP="instance_url"
INSTANCE_URL=$(jsonval)

# BEGIN TESTS
echo "----------------------"
echo "Test AppsRestResource"
echo "----------------------"
curl $INSTANCE_URL/services/apexrest/channel4_apps -H 'Authorization: Bearer '$ACCESS_TOKEN
echo
echo "----------------------"
echo
echo "----------------------"
echo "Test SessionRestResource"
echo "----------------------"
curl $INSTANCE_URL/services/apexrest/channel4_upload/ -H 'Authorization: Bearer '$ACCESS_TOKEN -H 'Content-Type: application/json' -d @session.json
echo
echo "----------------------"


echo "Tests Done"
echo
echo
