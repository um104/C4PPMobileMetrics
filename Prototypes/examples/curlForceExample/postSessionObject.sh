echo
echo 'Be sure to change the <instance_name> and <access_token>!'
echo
curl https://na11.salesforce.com/services/apexrest/channel4_upload/ -H 'Authorization: Bearer 00DG0000000BzRT!AR4AQM5ocP7jD0oRPIMIsj1CoFKxIv70qW.mpiZniO_7v3YtTLruVFDtu1hNjF7a780QnC3xHmcl9kttn1hS8T0_T23.Lpyh' -H 'Content-Type: application/json' -d @session.json

