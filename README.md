# Chess-Java

I have created a Chess game which follows all the rules according to FIDE (The International Chess Federation). The game has been written in Java and uses JavaFX to display the visuals. I have implemented a MVC (Model View Controller) architecture so that the Model and the View have no dependancies between them, meaning that theoretically I can change the View or the Model without changing the other.  

You can play 2 player with both the White and Black Pieces being controlled by the user. You can also play 1 Player against StockFish, the best Chess engine in the world (https://stockfishchess.org/about/)


Setting up the code:
  
  You must configure your IDE for use Java 11 and JavaFX. The program also uses a Stockfish commandline application which I have removed from the github.
  To add stockfish you must download the necessary file from here (https://stockfishchess.org/download/), then move the application (.exe) into a new folder in your project called 'Stockfish'. Then rename the application file to 'stockfish.exe'  


START GAME:



Play against Stockfish as White:

![1 Player White](https://user-images.githubusercontent.com/67894560/165652029-d5a8402f-1bad-43ee-8197-b87768632ec6.gif)




Play against Stockfish as Black:

![1 Player Black](https://user-images.githubusercontent.com/67894560/165653064-ee8390fa-a0c3-4ec4-b604-2134051ccaae.gif)



2 Player Game:

![2 Player](https://user-images.githubusercontent.com/67894560/165653676-a259129d-1d75-4a96-a2da-5a67bd1ebe74.gif)



MOVES:


En Passant:

![En Passant](https://user-images.githubusercontent.com/67894560/165654251-dbe0c774-1724-4a74-a62b-d9c41838dacf.gif)


Castling:

![Castling](https://user-images.githubusercontent.com/67894560/165663667-2f74a980-92a4-4447-8ef7-b95a5a3f858d.gif)


Promotion:

![Promotion](https://user-images.githubusercontent.com/67894560/165665554-65d4cfaf-202e-46b8-818f-3813376cf503.gif)







GAME OVER:


White Wins:

![White Wins](https://user-images.githubusercontent.com/67894560/165656389-cd659eec-dbe9-42bd-809d-90f23f6fb7a0.gif)


Black Wins:

![Black Wins](https://user-images.githubusercontent.com/67894560/165657257-10a0a6a4-af79-4999-b4c5-9224a9d2ec87.gif)


Stalemate:

![Stalemate](https://user-images.githubusercontent.com/67894560/165658198-c699ab32-57b2-4388-b868-e9e7ab9bba0a.gif)


50 Move Rule:

![50 Rule Move](https://user-images.githubusercontent.com/67894560/165659743-4160eab0-03dc-4788-b2b8-e7cc291a20d5.gif)


Repetition:

![Repetition](https://user-images.githubusercontent.com/67894560/165660410-83f67984-37a7-4668-945e-9e496a1e3f04.gif)


Insufficient Material:

![Insufficient Material](https://user-images.githubusercontent.com/67894560/165661091-39d94d1e-2e61-496e-88ea-f98fe57d2496.gif)


Play Again:

![Play Again](https://user-images.githubusercontent.com/67894560/165665949-5759be89-0226-4e15-ba63-b3f38d1c909b.gif)

