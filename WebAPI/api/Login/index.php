<?php

// database connection
$host = "localhost";
$db = "u752546149_db";
$user = "u752546149_admin";
$password = "!2k#k?T7KhuHFEt@";

// name of database tables
$userTablename = "User";
$hashTablename = "Hash";

// table columns, id column needs to be first
$userIDColumn = "userID";
$usernameColumn = "username";
$hashColumn = "hash";

// end configuration
/*****************************************************************************/

$options = [
  PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
  PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
  PDO::ATTR_EMULATE_PREPARES   => false
];

// create PDO object for database connection
try {
    $pdo = new PDO("mysql:host=$host;dbname=$db;", $user, $password, $options);
} catch (PDOException $e) {
    http_response_code(500);
    exit(1);
}

// get body of request
$requestBody = json_decode(file_get_contents('php://input'), true);

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // POST request received at https://swiftbook.co/api/Login

    $success = false;

    try {
        // Construct SELECT query
        $userIDStmtString = "SELECT " . $userIDColumn . " FROM "
            . $userTablename . " WHERE " . $usernameColumn . " = '"
            . $requestBody[$usernameColumn] . "';";

        // prepare the statement
        $userIDStmt = $pdo->prepare($userIDStmtString);

        // Execute query
        if ($userIDStmt->execute()) {
            // Statement executed successfully
            $userRow = $userIDStmt->fetch();

            if ($userRow[$userIDColumn]) {
                // Construct SELECT query
                $hashStmtString = "SELECT " . $hashColumn . " FROM "
                    . $hashTablename . " WHERE " . $userIDColumn . " = "
                    . $userRow[$userIDColumn] . ";";

                // prepare the statement
                $hashStmt = $pdo->prepare($hashStmtString);

                // Execute query
                if ($hashStmt->execute()) {
                    // Statement executed successfully
                    $hashRow = $hashStmt->fetch();

                    if ($hashRow[$hashColumn]) {
                        echo $hashRow[$hashColumn];

                        $success = true;
                    }
                }
            }
        }
    } catch (Exception $e) {
        // Error executing statement, set response code
        echo $e->getMessage();
    }

    http_response_code(($success ? 200 : 418));
} else {
    header("Location: https://swiftbook.co");
}
