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
$userColumns = [
  "userID",
  // "organization",
  "username",
  "email",
  "firstName",
  "lastName",
  "administrator"
];
$hashColumns = [
  "userID",
  "hash"
];

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

// count the number of columns
$columnCount = count($userColumns);

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // POST request received at https://swiftbook.co/api/User

    if($_GET["login"] === "true" || $requestBody["login"] === "true") {
        // Login POST request received
        // https://swiftbook.co/api/User/Login
        try {
            // Construct SELECT query
            $userIDStmtString = "SELECT " . $userColumns[0] . " FROM "
            . $userTablename . " WHERE " . $userColumns[1] . " = '"
            . $requestBody[$userColumns[1]] . "';";

            // prepare the statement
            $userIDStmt = $pdo->prepare($userIDStmtString);

            // Execute query
            if ($userIDStmt->execute()) {
                // Statement executed successfully
                $userRow = $userIDStmt->fetch();

                if($userRow[$userColumns[0]]) {
                    // Construct SELECT query
                    $hashStmtString = "SELECT " . $hashColumns[1] . " FROM "
                    . $hashTablename . " WHERE " . $userColumns[0] . " = "
                    . $userRow[$userColumns[0]] . ";";

                    // prepare the statement
                    $hashStmt = $pdo->prepare($hashStmtString);

                    // Execute query
                    if ($hashStmt->execute()) {
                        // Statement executed successfully
                        $hashRow = $hashStmt->fetch();

                        if($hashRow[$hashColumns[1]]) {

                            echo $hashRow[$hashColumns[1]];

                            // set response code
                            http_response_code(200);
                        } else {
                            // set response code
                            http_response_code(418);
                        }
                    } else {
                        // set response code
                        http_response_code(400);
                    }
                } else {
                    // set response code
                    http_response_code(418);
                }
            } else {
                // set response code
                http_response_code(400);
            }
        } catch (Exception $e) {
            // Error executing statement, set response code
            echo $e->getMessage();
            http_response_code(400);
        }
    } else {
        // Not logging in; Create a new user

        // Begin a transaction, turning off autocommit
        $pdo->beginTransaction();

        // Change the database schema and data
        try {
            // Construct INSERT query with placeholders (values are binded below)
            $userStmtString = "INSERT INTO " . $userTablename . " (";
            for ($i = 1; $i < $columnCount; $i++) {
                $userStmtString .= $userColumns[$i] . ($i+1 == $columnCount ? "":", ");
            }
            $userStmtString .=  ") VALUES (";
            for ($i = 1; $i < $columnCount; $i++) {
                $userStmtString .= ":" . $userColumns[$i]
                . ($i+1 == $columnCount ? "":", ");
            }
            $userStmtString .= ")";

            // prepare the statement
            $userStmt = $pdo->prepare($userStmtString);

            // bind values to statement
            for ($i = 1; $i < $columnCount; $i++) {
                $userStmt->bindParam(":" . $userColumns[$i], $requestBody[$userColumns[$i]]);
            }

            // Execute query
            if ($userStmt->execute()) {
                // Statement executed successfully

                // get new user id
                $new_id = $pdo->lastInsertId();

                // Construct INSERT query with placeholders (values are binded below)
                $hashStmtString = "INSERT INTO " . $hashTablename . " ("
                   . $hashColumns[1][0] . ", " . $hashColumns[1][1] . ") VALUES ("
                   . ":" . $hashColumns[1][0] . ", :" . $hashColumns[1][1] . ")";

                // prepare the statement
                $hashStmt = $pdo->prepare($hashStmtString);

                // bind values to statement
                $hashStmt->bindParam(":" . $hashColumns[1][0], $new_id);
                $hashStmt->bindParam(":" . $hashColumns[1][1], $requestBody[$hashColumns[1][1]]);

                // Execute query
                if ($hashStmt->execute()) {
                    // commit changes to database
                    $pdo->commit();

                    // TODO fix Location header (not being set)
                    // set location header pointing to new user
                    header("Location: /api/User/" + $new_id);
                    // set response code 201 Created
                    http_response_code(201);
                } else {
                    throw new Exception('New Hash transaction failed');
                }
            } else {
                // Roll back changes
                $pdo->rollBack();
                // set response code 424 Failed Dependency
                http_response_code(424);
            }
        } catch (Exception $e) {
            // Roll back changes
            $pdo->rollBack();
            // Error executing statement, set response code
            // http_response_code(400);
            echo $e->getMessage();
        }
    }
} elseif ($_SERVER['REQUEST_METHOD'] === 'PUT'
    && (isset($_GET[$userColumns[0]]) && !empty($_GET[$userColumns[0]]))) {
    // PUT request received with id
    // https://swiftbook.co/api/User
    try {
        // Construct UPDATE query with id
        $userStmtString = "UPDATE " . $userTablename . " SET ";
        for ($i = 1; $i < $columnCount; $i++) {
            $userStmtString .= $userColumns[$i] .  " = :" . $userColumns[$i]
                . ($i+1 == $columnCount ? " ":", ");
        }
        $userStmtString .= "WHERE " . $userColumns[0] . " = :" . $userColumns[0];

        // prepare the statement
        $userStmt = $pdo->prepare($userStmtString);

        // bind values to statement
        for ($i = 1; $i < $columnCount; $i++) {
            $userStmt->bindParam(":" . $userColumns[$i], $requestBody[$userColumns[$i]]);
        }

        // bind id to statement
        $userStmt->bindParam(':' . $userColumns[0], $_GET[$userColumns[0]]);

        // Execute query
        if ($userStmt->execute()) {
            // Statement executed successfully, set response code
            http_response_code(204);
        } else {
            // set response code
            http_response_code(400);
        }
    } catch (Exception $e) {
        // Error executing statement, set response code
        http_response_code(400);
    }
} elseif ($_SERVER['REQUEST_METHOD'] === 'DELETE'
    && (isset($_GET[$userColumns[0]]) && !empty($_GET[$userColumns[0]]))) {
    // DELETE request received with id
    // https://swiftbook.co/api/User/0
    try {
        // Construct DELETE query with id
        $userStmtString = "DELETE FROM " . $userTablename
        . " WHERE " . $userColumns[0] . " = :" . $userColumns[0];

        // prepare the statement
        $userStmt = $pdo->prepare($userStmtString);

        // bind id to statement
        $userStmt->bindParam(':' . $userColumns[0], $_GET[$userColumns[0]]);

        // Execute query
        if ($userStmt->execute()) {
            // Query executed successfully
            // set response code
            http_response_code(204);
        } else {
            // set response code
            http_response_code(400);
        }
    } catch (Exception $e) {
        // Error executing query, set response code
        http_response_code(400);
    }
} elseif ($_SERVER['REQUEST_METHOD'] === 'GET'
    && (isset($_GET[$userColumns[0]]) && !empty($_GET[$userColumns[0]]))) {
    // GET request received with id
    // https://swiftbook.co/api/User/0
    try {
        // Construct SELECT query with id
        $userStmtString = "SELECT * FROM " . $userTablename
        . " WHERE " . $userColumns[0] . " = :" . $userColumns[0];

        // prepare the statement
        $userStmt = $pdo->prepare($userStmtString);

        // bind id to statement
        $userStmt->bindParam(':' . $userColumns[0], $_GET[$userColumns[0]]);

        // Execute query
        if ($userStmt->execute()) {
            // Query executed successfully, set response code
            http_response_code(200);

            if ($userStmt->rowCount() > 0) {
                // Query returned results
                $resultArray = array();
                $tempArray = array();

                while ($row = $userStmt->fetch()) {
                    // Add each row into results array
                    $tempArray = $row;
                    array_push($resultArray, $tempArray);
                }
                // set response code
                http_response_code(200);
                // set Content-Type header
                header('Content-Type: application/json');
                // echo the JSON encoded array
                echo json_encode($resultArray);
            } else {
                // No rows returned, set response code
                http_response_code(404);
            }
        } else {
            // set response code
            http_response_code(400);
        }
    } catch (Exception $e) {
        // Error executng query, set response code
        http_response_code(400);
    }
} elseif ($_SERVER['REQUEST_METHOD'] === 'GET'
    && (isset($_GET[$userColumns[1]]) && !empty($_GET[$userColumns[1]]))) {
    // GET request received with Username
    // https://swiftbook.co/api/User/ByUsername/brianjmorris
    try {
        // Construct SELECT query with id
        $userStmtString = "SELECT * FROM " . $userTablename
        . " WHERE " . $userColumns[1] . " = :" . $userColumns[1];

        // prepare the statement
        $userStmt = $pdo->prepare($userStmtString);

        // bind id to statement
        $userStmt->bindParam(':' . $userColumns[1], $_GET[$userColumns[1]]);

        // Execute query
        if ($userStmt->execute()) {
            // Query executed successfully, set response code
            http_response_code(200);

            if ($userStmt->rowCount() > 0) {
                // Query returned results
                $resultArray = array();
                $tempArray = array();

                while ($row = $userStmt->fetch()) {
                    // Add each row into results array
                    $tempArray = $row;
                    array_push($resultArray, $tempArray);
                }
                // set response code
                http_response_code(200);
                // set Content-Type header
                header('Content-Type: application/json');
                // echo the JSON encoded array
                echo json_encode($resultArray);
            } else {
                // No rows returned, set response code
                http_response_code(404);
            }
        } else {
            // set response code
            http_response_code(400);
        }
    } catch (Exception $e) {
        // Error executng query, set response code
        http_response_code(400);
    }
} elseif ($_SERVER['REQUEST_METHOD'] === 'GET') {
    // GET request received
    // https://swiftbook.co/api/User
    try {
        // Construct SELECT all query
        $userStmt = $pdo->prepare("SELECT * FROM $userTablename");

        // Execute query
        if ($userStmt->execute()) {
            // Query executed successfully, set response code
            http_response_code(204);
            if ($userStmt->rowCount() > 0) {
                // Query returned results
                $resultArray = array();
                $tempArray = array();

                while ($row = $userStmt->fetch()) {
                    // Add each row into results array
                    $tempArray = $row;
                    array_push($resultArray, $tempArray);
                }
                // set response code
                http_response_code(200);
                // set Content-Type header
                header('Content-Type: application/json');
                // echo JSON encoded array
                echo json_encode($resultArray);
            } else {
                // No rows returned, set response code
                http_response_code(204);
            }
        } else {
            // set response code
            http_response_code(400);
        }
    } catch (Exception $e) {
        // Error executing query, set response code
        http_response_code(400);
    }
} else {
    // set response code
    http_response_code(400);
}
