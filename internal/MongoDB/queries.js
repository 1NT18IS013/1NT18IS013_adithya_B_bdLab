// Demonstrate usage of $match, $group and
// aggregate pipeline
db.results.aggregate(
    [
        {
            $match: { Pass: true }
        },
        {
            $group: { _id: "$USN" }
        }
    ]
);

// Demonstrate Map-reduce aggregate function
var mapper = function () {
    emit(this.USN, this.Subject1)
};

var reducer = function (USN, Subject1) {
    return Array.avg(Subject1)
};

db.results.mapReduce(mapper, reducer, {
    out: "output"
});

// Count no. of students who passed at least 2 subjects
db.results.aggregate(
    [
        {
            $match: {
                $and: [
                    { Subject1: { $gt: 35 } },
                    { Subject2: { $gt: 35 } }
                ]
            }
        },
        {
            $count: "passed"
        }
    ]
);

// Demonstrate Alter/update and drop commands
db.results.update(
    {
        USN: 9
    },
    {
        $set: {
            Name: "Soniya singh",
            Subject2: 80
        }
    }
);

db.output.drop();
