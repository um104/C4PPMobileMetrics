echo
echo 'Be sure to change the username, password, and security token here!'
echo 'See https://na11.salesforce.com/help/doc/en/user_security_token.htm for more info'
echo
curl https://login.salesforce.com/services/oauth2/token -d "grant_type=password" -d "client_id=3MVG9y6x0357HlecylRTsJx8y_qIjGh9Z7CQEA0bTx5xHsmQRBBXZaOldH3._q.NTUYlX1A4JdiewYx5qMvU4" -d "client_secret=603269615811711635" -d "username=c4@capstone.com" -d "password=escape76FVp2n6LFH2kPrAXfrUMzinne"
echo
