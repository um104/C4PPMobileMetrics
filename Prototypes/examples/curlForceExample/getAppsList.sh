# Config variables (SET THESE UP FIRST!)
INSTANCE_URL="https://na9.salesforce.com"
ACCESS_TOKEN="0DE0000000dkGn!ARwAQAEJikUySaAvwT3AZ2YdvfTBzFYe2561i80L4tdThOKCIA7sL.t_86sA6WIyQO0YVXn7jDqnavvMTE5QSPZqOCLTINLq"


get_apps_list()
{
	curl $INSTANCE_URL/services/apexrest/C4PPMM/channel4_apps -H 'Authorization: Bearer '$ACCESS_TOKEN
}


count="5"
for i in $(seq 1 $count)
do
	echo 'get_apps_list '$i':'
	get_apps_list
	echo
	echo
done












# echo
# echo 'Be sure to change the <instance_name> and <access_token>!'
# echo
# curl https://na9.salesforce.com/services/apexrest/C4PPMM/channel4_apps -H 'Authorization: Bearer 00DE0000000dkGn!ARwAQAEJikUySaAvwT3AZ2YdvfTBzFYe2561i80L4tdThOKCIA7sL.t_86sA6WIyQO0YVXn7jDqnavvMTE5QSPZqOCLTINLq'
# echo
