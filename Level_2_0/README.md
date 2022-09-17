# Elevator Maintenance

## Description

You’ve been assigned the onerous task of elevator maintenance - ugh! It wouldn’t be so bad, except that all the elevator documentation has been lying in a disorganized pile at the bottom of a filing cabinet for years, and you don’t even know what elevator version numbers you’ll be working on.

<details><summary>Details about this assignment</summary>

> Elevator versions are represented by a series of numbers, divided up into major, minor and revision integers. New versions of an elevator increase the major number, e.g. 1, 2, 3, and so on. When new features are added to an elevator without being a complete new version, a second number named minor can be used to represent those new additions, e.g. 1.0, 1.1, 1.2, etc. Small fixes or maintenance work can be represented by a third number named revision, e.g. 1.1.1, 1.1.2, 1.2.0, and so on. The number zero can be used as a major for pre-release versions of elevators, e.g. 0.1, 0.5, 0.9.2, etc (Commander Lambda is careful to always beta test her new technology, with her loyal henchmen as subjects!).
> 
> Given a list of elevator versions represented as strings, write a function solution(l) that returns the same list sorted in ascending order by major, minor, and revision number so that you can identify the current elevator version. The versions in list l will always contain major numbers, but minor and revision numbers are optional. If the version contains a revision number, then it will also have a minor number.
> 
> For example, given the list l as ["1.1.2", "1.0", "1.3.3", "1.0.12", "1.0.2"], the function solution(l) would return the list ["1.0", "1.0.2", "1.0.12", "1.1.2", "1.3.3"]. If two or more versions are equivalent but one version contains more numbers than the others, then these versions must be sorted ascending based on how many numbers they have, e.g ["1", "1.0", "1.0.0"]. The number of elements in the list l will be at least 1 and will not exceed 100.

</details>

## Test Cases

Your code should pass the following test cases. Note that it may also be run against hidden test cases not shown here.

### Test Case 1

Inputs:

    (string list) l = ["1.11", "2.0.0", "1.2", "2", "0.1", "1.2.1", "1.1.1", "2.0"]

Output:

    (string list) ["0.1", "1.1.1", "1.2", "1.2.1", "1.11", "2", "2.0", "2.0.0"]
    
### Test Case 2

Inputs:

    (string list) l = ["1.1.2", "1.0", "1.3.3", "1.0.12", "1.0.2"]

Output:

    (string list) ["1.0", "1.0.2", "1.0.12", "1.1.2", "1.3.3"]

## Approach 

The problem statement is fairly straight forward. Given a list of versions, sort them in ascending order with order of precedence : <br/>
**major > minor > revision** <br/>
This is simple enough, let’s come to the edge cases.

Minor and revision numbers are optional
If two or more versions are equivalent but one version contains more numbers than the others, then these versions must be sorted in ascending order based on how many numbers they have.

Before addressing these, lets first see the solution code

```
public class ElevatorMaintenance {

    public static String[] solution(String[] l) {

        Arrays.sort(l, (s1, s2) -> {

            final String[] s1versions = s1.split("\\.");
            final String[] s2versions = s2.split("\\.");

            int majorVersionComparison = compareByVersionType(s1versions, s2versions, 0);
            if (majorVersionComparison != 0) {
                return majorVersionComparison;
            }
            int minorVersionComparison = compareByVersionType(s1versions, s2versions, 1);
            if (minorVersionComparison != 0) {
                return minorVersionComparison;
            }
            int revisionVersionComparison = compareByVersionType(s1versions, s2versions, 2);
            if (revisionVersionComparison != 0) {
                return revisionVersionComparison;
            }
            return s1versions.length - s2versions.length;
        });

        return l;
    }

    private static int compareByVersionType(String[] s1versions, String[] s2versions, int versionType) {

        final int version1 = versionType >= s1versions.length ? 0 : Integer.parseInt(s1versions[versionType]);
        final int version2 = versionType >= s2versions.length ? 0 : Integer.parseInt(s2versions[versionType]);

        return version1 - version2;
    }

    public static void main(String[] args) {

        System.out.println(String.join(",", ElevatorMaintenance.solution(
            new String[]{"1.1.2", "0.1.0", "0.1"}
        )));
    }
}

```
