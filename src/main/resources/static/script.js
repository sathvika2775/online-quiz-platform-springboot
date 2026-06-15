let currentQuizId = null;
let timer;
let timeLeft = 60;

async function createQuiz() {

    const category = document.getElementById("category").value.trim();
    const numQ = document.getElementById("numQ").value;

    if (!category) {
        alert("Please enter a category");
        return;
    }

    if (numQ < 1 || numQ > 20) {
        alert("Questions must be between 1 and 20");
        return;
    }

    try {

        clearQuizData();

        const response = await fetch(
            `http://localhost:8080/quiz/create?category=${category}&numQ=${numQ}`
        );

        if (!response.ok) {
            throw new Error("Quiz creation failed");
        }

        const quiz = await response.json();

        currentQuizId = quiz.id;

        document.getElementById("result").innerHTML =
            `<h3>✅ ${quiz.title} Created Successfully</h3>`;

        loadQuestions(quiz.id);
        startTimer();

    } catch (error) {

        console.error(error);
        alert("Unable to create quiz");

    }
}

async function loadQuestions(id) {

    try {

        const response = await fetch(
            `http://localhost:8080/quiz/questions/${id}`
        );

        const questions = await response.json();

        let output = "";

        questions.forEach(q => {

            output += `
            <div class="question-card">

                <h3>${q.questionTitle}</h3>

                <label>
                    <input type="radio" name="${q.id}" value="${q.option1}">
                    ${q.option1}
                </label>
                <br>

                <label>
                    <input type="radio" name="${q.id}" value="${q.option2}">
                    ${q.option2}
                </label>
                <br>

                <label>
                    <input type="radio" name="${q.id}" value="${q.option3}">
                    ${q.option3}
                </label>
                <br>

                <label>
                    <input type="radio" name="${q.id}" value="${q.option4}">
                    ${q.option4}
                </label>

            </div>
            <br>
            `;
        });

        document.getElementById("questions").innerHTML = output;

        document.getElementById("submitSection").innerHTML =
            `
            <button id="submitBtn" onclick="submitQuiz()">
                Submit Quiz
            </button>
            `;

    } catch (error) {

        console.error(error);
        alert("Unable to load questions");

    }
}

async function submitQuiz() {
    clearInterval(timer);
    const selectedAnswers =
        document.querySelectorAll(
            'input[type="radio"]:checked'
        );

    let responses = [];

    selectedAnswers.forEach(answer => {

        responses.push({
            id: parseInt(answer.name),
            response: answer.value
        });

    });

    console.log(responses);

    try {

        document.getElementById("submitBtn").disabled = true;

        const response = await fetch(
            `http://localhost:8080/quiz/submit/${currentQuizId}`,
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(responses)
            }
        );

        const result = await response.json();

            let message = "";
            let color = "";
            if(result.score ===0){
                message = "😢 Better Luck Next Time!";
                color="red";
            }
            else if(result.score === result.totalQuestions){
                message = "🏆 Excellent!";
                color="green";
            }
            else if(result.score >= result.totalQuestions / 2){
                message = "😊 Good Job!";
                color="yellow";
            }
        else{
            message = "😢 Better Luck Next Time!";
            color="orange";
        }

        document.getElementById("score").innerHTML =
        `
        <div class="score-card">

            <h2 style="color:${color}">
                🎯 Score : ${result.score}/${result.totalQuestions}
            </h2>

            <h3 style="color:${color}">
                ${message}
            </h3>

        </div>
        `;
        document.getElementById("restartSection").innerHTML =
            `
            <button onclick="restartQuiz()">
                Restart Quiz
            </button>
            `;

    } catch (error) {

        console.error(error);
        alert("Submission failed");

    }
}

function restartQuiz() {

    currentQuizId = null;
    clearInterval(timer);
    document.getElementById("timer").innerHTML = "";

    document.getElementById("category").value = "";
    document.getElementById("numQ").value = "";

    clearQuizData();
}

function clearQuizData() {

    document.getElementById("result").innerHTML = "";
    document.getElementById("questions").innerHTML = "";
    document.getElementById("submitSection").innerHTML = "";
    document.getElementById("score").innerHTML = "";
    document.getElementById("restartSection").innerHTML = "";
}

function startTimer() {

    clearInterval(timer);

    timeLeft = 60;

    document.getElementById("timer").innerHTML =
        `<h3>⏳ Time Left: ${timeLeft}s</h3>`;

    timer = setInterval(() => {

        timeLeft--;

        document.getElementById("timer").innerHTML =
            `<h3>⏳ Time Left: ${timeLeft}s</h3>`;

        if (timeLeft <= 0) {

            clearInterval(timer);

            alert("Time is up!");

            submitQuiz();
        }

    }, 1000);
}