# PongServer
The Pong Game Engine used for an AI Experiment

PongClient Protocol:
  		    |   Query			   |    Response			
--------------------+------------------------------+-----------------------
	Commands:   |   "MOVE " + {"up [n]",	   |	"OK"
		    |  		"down [n]",	   |
		    |  		"stop"}   	   |
	  	    |    			   |	"ERROR"
		    |   			   |   
	Requests:   |   "GET " + {"screenSize",    |    "[sizeX],[sizeY]"
		    |    	"ballLocation",	   |    "[ballX],[ballY]"
		    |    	"ballSize",	   |    "[ballD]"
		    |    	"BallVelocity",	   |    "[ballDX],[ballDY]"
		    |    	"myPaddle",	   |    "[pX],[pY],[pV]"
		    |    	"opponentPaddle",  |    "[pX],[pY],[pV]"
		    |    	"paddleSize",	   |    "[pW],[pH]"
		    |    	"score"}	   |    "[myScore],[opponentScore]"
		    |				   |    "ERROR"
                    |                              |
        Status:     |   "DONE"                     |    "DONE"
                    |                              |    "NO"
                    |                              |    "ERROR"

What's New:

What's New:

V1.2    Protocol        *Added "DONE" to display current running state of Server.  If response is "DONE" the client socket should be closed.

        Ball.java       *Set default ball speed to 20
        
        Player.java     *Fixed "ERROR" responses unexpectedly closing server.
                        *Added log method to verbos output from the server to command line.
                        
        PongApplet.java *Added log method to verbox output from the game to command line.
                        *Modified running variable for protocol access
                        
V1.1	Ball.java	*Split update into intervals based on speed.
			*Fixed passing through paddles when ball is moving exceptionally fast.
			*Removed unused if statements.
