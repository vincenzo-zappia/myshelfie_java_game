# MyShelfie – Java Networked Board Game

## Introduction
The full report with images is available in the `Documentation` section.  
This document summarizes the key features, design decisions, and implementation details of the board game **MyShelfie**.

## Game Overview

- **Board:** Implemented as a 9x9 matrix of `BoardTile` objects. Each tile has an `active` flag to indicate playable squares, giving the board its unique shape.  
- **Bookshelf:** Each `Player` has an instance of `Bookshelf`; the `Game` class does not access it directly.  
- **Bag:** Generates all 132 game cards (22 per type for 6 types). Each card has:  
  - a type enumeration  
  - a name from one of three possible images per type  
  Cards are distributed via:

  ```
      for (int i = 0; i < 6; i++)
          for(int j = 0; j < 22; j++)
              bag.add(new Card(cardImgName[i][j % 3], CardType.values()[i]));
  ```

- **Common Goals:** Each goal has its own class implementing the specific algorithm. At game start, two goals are randomly selected from the twelve possible using `CommonGoalFactory`.

## Game Mechanics

- `Game` implements methods for all interactions, e.g., validating a card selection, removing it from the board, and placing it in a player’s bookshelf.  
- `GameController` enforces logical order and turn rules:  
  - Only the current player may act.  
  - Actions must follow the correct sequence (e.g., selection before placement).  
- `TurnManager` manages turns using a list of player usernames.  
  - The first player is at index 0; the player to their right is the last index.  
  - `nextTurn` iterates circularly until a player fills their bookshelf; iteration ends at the player to the right of the first player.

## User Interface: CLI

The CLI runs on a separate thread from `Main` and manages game flow through four handlers:

1. `usernameHandler` — selects a unique username.  
2. `lobbyHandler` — creates or joins a lobby and waits for the game to start.  
3. `gameHandler` — sends game commands and displays game entities.  
4. `endGameHandler` — handles replay or exit after a game ends or a player disconnects.

Handlers synchronize with the server using `Object` locks.  
`VirtualModel` tracks the current state of game entities.

## Communication

- Messages are classes extending `Message` (implements `Serializable`), allowing transmission of game objects via `ObjectInputStream`/`ObjectOutputStream`.  
- `NetworkInterface` defines `sendMessage` and `receiveMessage`, extended by `Client` and `ClientHandler`.  
- `Server` responsibilities:  
  - Track active player usernames and lobbies  
  - Accept new connections  
  - For each connection, create a `ClientHandler` associated with a `VirtualView` (tracked via `NetworkPlayer`)  

- Server messages are divided into:  
  1. Messages from `GameController` reflecting entity or game state changes  
  2. Messages from `Lobby` related to connection phase or chat  

- `Client` uses the Observer pattern to notify `ClientController` when new messages arrive.

## Safe Disconnection

If a player disconnects unexpectedly, an exception is generated. The system forces the remaining players in the lobby to a screen where they can:

- Create a new lobby  
- Join an existing lobby  
- Exit the application
