<?php

// database connection
$host = "localhost";
$db = "u752546149_db";
$user = "u752546149_admin";
$password = "!2k#k?T7KhuHFEt@";

// name of database table
$tablename = "User";

// table columns, id column needs to be first
$columns = [
  "UserID",
  "Username",
  "Email",
  "FirstName",
  "LastName",
  "Organization",
  "Administrator"
];

// end configuration
/*****************************************************************************/

$options = [
  PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
  PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
  PDO::ATTR_EMULATE_PREPARES   => false
];

$dsn = "mysql:host=$host;dbname=$db;";

// create PDO object for database connection
try {
    $pdo = $conn = new PDO($dsn, $user, $password, $options);
} catch (PDOException $e) {
    http_response_code(500);
    exit(1);
}

// get body of request
$requestBody = json_decode(file_get_contents('php://input'), true);

// count the number of columns
$columnCount = count($columns);

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // POST request received
    // http://www.cosc3506.ga/api/User/index.php
    try {
        // Construct INSERT query
        $stmtString = "INSERT INTO " . $tablename . " (";
        for ($i = 1; $i < $columnCount; $i++) {
            $stmtString .= $columns[$i] . ($i+1 == $columnCount ? "":", ");
        }
        $stmtString .=  ") VALUES (";
        for ($i = 1; $i < $columnCount; $i++) {
            $stmtString .= ":" . $columns[$i]
            . ($i+1 == $columnCount ? "":", ");
        }
        $stmtString .= ")";

        // prepare the statement
        $stmt = $pdo->prepare($stmtString);

        // bind values to statement
        for ($i = 1; $i < $columnCount; $i++) {
            $stmt->bindParam(":" . $columns[$i], $requestBody[$columns[$i]]);
        }

        // Execute query
        if ($stmt->execute()) {
            // Statement executed successfully
            // echo updated values
            $new_id = $pdo->lastInsertId();
            // set response code
            http_response_code(201);
        } else {
            // set response code
            http_response_code(400);
        }
    } catch (Exception $e) {
        // Error executing statement, set response code
        http_response_code(400);
    }
} elseif ($_SERVER['REQUEST_METHOD'] === 'PUT'
    && (isset($_GET[$columns[0]]) && !empty($_GET[$columns[0]]))) {
    // PUT request received with id
    // http://www.cosc3506.ga/api/User/index.php
    try {
        // Construct UPDATE query with id
        $stmtString = "UPDATE " . $tablename . " SET ";
        for ($i = 1; $i < $columnCount; $i++) {
            $stmtString .= $columns[$i] .  " = :" . $columns[$i]
                . ($i+1 == $columnCount ? " ":", ");
        }
        $stmtString .= "WHERE " . $columns[0] . " = :" . $columns[0];

        // prepare the statement
        $stmt = $pdo->prepare($stmtString);

        // bind values to statement
        for ($i = 1; $i < $columnCount; $i++) {
            $stmt->bindParam(":" . $columns[$i], $requestBody[$columns[$i]]);
        }

        // bind id to statement
        $stmt->bindParam(':' . $columns[0], $_GET[$columns[0]]);

        // Execute query
        if ($stmt->execute()) {
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
    && (isset($_GET[$columns[0]]) && !empty($_GET[$columns[0]]))) {
    // DELETE request received with id
    // http://www.cosc3506.ga/api/User/index.php?UserID=0
    try {
        // Construct DELETE query with id
        $stmtString = "DELETE FROM " . $tablename
        . " WHERE " . $columns[0] . " = :" . $columns[0];

        // prepare the statement
        $stmt = $pdo->prepare($stmtString);

        // bind id to statement
        $stmt->bindParam(':' . $columns[0], $_GET[$columns[0]]);

        // Execute query
        if ($stmt->execute()) {
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
    && (isset($_GET[$columns[0]]) && !empty($_GET[$columns[0]]))) {
    // GET request received with id
    // http://www.cosc3506.ga/api/User/index.php?UserID=0
    try {
        // Construct SELECT query with id
        $stmtString = "SELECT * FROM " . $tablename
        . " WHERE " . $columns[0] . " = :" . $columns[0];

        // prepare the statement
        $stmt = $pdo->prepare($stmtString);

        // bind id to statement
        $stmt->bindParam(':' . $columns[0], $_GET[$columns[0]]);

        // Execute query
        if ($stmt->execute()) {
            // Query executed successfully, set response code
            http_response_code(200);

            if ($stmt->rowCount() > 0) {
                // Query returned results
                $resultArray = array();
                $tempArray = array();

                while ($row = $stmt->fetch()) {
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
    // http://www.cosc3506.ga/api/User/index.php
    try {
        // Construct SELECT all query
        $stmt = $pdo->prepare("SELECT * FROM $tablename");

        // Execute query
        if ($stmt->execute()) {
            // Query executed successfully, set response code
            http_response_code(204);
            if ($stmt->rowCount() > 0) {
                // Query returned results
                $resultArray = array();
                $tempArray = array();

                while ($row = $stmt->fetch()) {
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
