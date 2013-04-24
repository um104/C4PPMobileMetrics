# Config variables (SET THESE UP FIRST!)
INSTANCE_URL="https://na9.salesforce.com"
ACCESS_TOKEN="0DE0000000dkGn!ARwAQAEJikUySaAvwT3AZ2YdvfTBzFYe2561i80L4tdThOKCIA7sL.t_86sA6WIyQO0YVXn7jDqnavvMTE5QSPZqOCLTINLq"


JSON_SESSION='{"sessions":[{"dt":"a","dc":"us","dac":"android_network_type_3","du":"c86c0019-b8cb-43d6-9f01-76a158fad773","dmo":"sdk","events":[],"dlc":"US","dma":"unknown","lv":"android_2.3","dms":null,"dov":"2.2","ct":1366240286,"av":"1.0","au":"2b9b47ca4e9178b076524b4-d8a060da-215f-11e2-5ebd-00ef75f32667","nca":"Android","u":"ce91b904-f394-4081-85f4-b386833db2bc","pa":1366155392,"nc":"us","an":"ButtonMash","pn":"edu.channel4.buttonmash.activity","dll":"en","dsdk":"8","dp":"Android"},{"dt":"a","dc":"us","dac":"android_network_type_3","du":"c86c0019-b8cb-43d6-9f01-76a158fad773","dmo":"sdk","events":[],"dlc":"US","dma":"unknown","lv":"android_2.3","dms":null,"dov":"2.2","ct":1366255915,"av":"1.0","au":"2b9b47ca4e9178b076524b4-d8a060da-215f-11e2-5ebd-00ef75f32667","nca":"Android","u":"b04c2f98-1164-4fc8-b211-3da125b90da7","pa":1366155392,"nc":"us","an":"ButtonMash","pn":"edu.channel4.buttonmash.activity","dll":"en","dsdk":"8","dp":"Android"}]}'


get_apps_list() 
{
	curl $INSTANCE_URL/services/apexrest/C4PPMM/channel4_apps -H 'Authorization: Bearer '$ACCESS_TOKEN
}

post_session() 
{
	curl --request POST $INSTANCE_URL/services/apexrest/C4PPMM/channel4_upload/ -H 'Authorization: Bearer '$ACCESS_TOKEN -H 'Content-Type: application/json' --data $JSON_SESSION
}


count="5"
for i in $(seq 1 $count)
do
	# echo 'get_apps_list '$i':'
	# get_apps_list
	echo
	echo

	echo 'post_session '$i':'
	post_session
	echo
	echo
done






# echo
# echo 'Be sure to change the <instance_name> and <access_token>!'
# echo
# curl https://na9.salesforce.com/services/apexrest/C4PPMM/channel4_apps -H 'Authorization: Bearer 00DE0000000dkGn!ARwAQAEJikUySaAvwT3AZ2YdvfTBzFYe2561i80L4tdThOKCIA7sL.t_86sA6WIyQO0YVXn7jDqnavvMTE5QSPZqOCLTINLq'
# echo

