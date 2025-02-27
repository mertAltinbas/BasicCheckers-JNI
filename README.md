<h1>Checkers Game</h1>

<p>The Checkers Game is a Java-based application that implements a classic checkers game with a graphical user interface. This project allows two players to compete against each other, featuring standard checkers rules, including piece movement, capturing, and kinging. The game also utilizes JNI (Java Native Interface) to enhance performance by integrating C++ for core game logic.</p>

<h2>Key Features:</h2>
<ul>
    <li><strong>Two-Player Mode:</strong> Players can take turns to move their pieces on the board.</li>
    <li><strong>Graphical User Interface (GUI):</strong> A user-friendly interface built with Swing, providing an interactive experience.</li>
    <li><strong>Piece Movement:</strong> Supports standard movement rules, including diagonal moves and capturing opponent pieces.</li>
    <li><strong>Kinging:</strong> Automatically promotes pieces to kings when they reach the opposite end of the board.</li>
    <li><strong>Game State Management:</strong> Tracks the game state, including player turns and available moves.</li>
    <li><strong>JNI Integration:</strong> Utilizes JNI to call C++ functions for core game logic, improving performance and efficiency.</li>
</ul>

<h2>Components:</h2>
<ul>
    <li><strong>Main Class:</strong> Initializes the game board and GUI, handling user interactions and game logic.</li>
    <li><strong>Board Class:</strong> Represents the checkers board, managing piece positions and game rules.</li>
    <li><strong>Piece Class:</strong> Represents individual pieces, including their type (regular or king) and movement capabilities.</li>
    <li><strong>JNI Class:</strong> Contains native methods for game logic implemented in C++, facilitating communication between Java and C++.</li>
    <li><strong>Test Class:</strong> Contains unit tests to ensure the functionality of the game logic and rules, including tests for JNI interactions.</li>
</ul>

<h2>Usage:</h2>
<ol>
    <li>Run the application to start the checkers game.</li>
    <li>Players take turns selecting and moving their pieces on the board.</li>
    <li>Capture opponent pieces by jumping over them, following the rules of movement.</li>
    <li>Reach the opposite end of the board to promote your piece to a king.</li>
    <li>The game ends when one player has no available moves left.</li>
</ol>

<h2>Requirements:</h2>
<ul>
    <li>Java Development Kit (JDK) 8 or higher</li>
    <li>C++ Compiler (for JNI integration)</li>
</ul>

<h2>Installation:</h2>
<p>Clone the repository and compile the Java files. Ensure that all dependencies are included for the GUI to function properly. For JNI, compile the C++ code and ensure the shared library is accessible to the Java application.</p>

<h2>Testing:</h2>
<p>The project includes unit tests written using JUnit to verify the functionality of the game logic. Tests cover:</p>
<ul>
    <li>Board initialization and piece placement</li>
    <li>Valid and invalid moves</li>
    <li>Piece capturing and kinging</li>
    <li>JNI method calls and interactions</li>
</ul>
<p>Run the tests to ensure that all functionalities work as expected and that the JNI integration is functioning correctly.</p>
