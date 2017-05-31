unless ARGV[0].empty?
	require "chatwork"

	# Create message
	ChatWork.api_key = "8f247d4a711fb2326012a8ad47243db3"
	ChatWork::Message.create(room_id: 75951589, body: "[info][To:2135825] [To:2198020] [To:2198033] [To:1979687] \n\n CI Build Success! \n\n Please Check! \n Link: #{ARGV[0]} [/info]")
end
