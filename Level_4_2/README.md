> <div id="top"></div>

# Bringing a Gun to a Guard Fight

##  Description

Uh-oh - you've been cornered by one of Commander Lambdas elite guards! Fortunately, you grabbed a beam weapon from an abandoned guard post while you were running through the station, so you have a chance to fight your way out. But the beam weapon is potentially dangerous to you as well as to the elite guard: its beams reflect off walls, meaning you'll have to be very careful where you shoot to avoid bouncing a shot toward yourself!

Luckily, the beams can only travel a certain maximum distance before becoming too weak to cause damage. You also know that if a beam hits a corner, it will bounce back in exactly the same direction. And of course, if the beam hits either you or the guard, it will stop immediately (albeit painfully).

<details><summary>Details about this assignment</summary>

> Write a function solution(dimensions, your_position, guard_position, distance) that gives an array of 2 integers of the width and height of the room, an array of 2 integers of your x and y coordinates in the room, an array of 2 integers of the guard's x and y coordinates in the room, and returns an integer of the number of distinct directions that you can fire to hit the elite guard, given the maximum distance that the beam can travel.
> 
> The room has integer dimensions [1 < x_dim <= 1250, 1 < y_dim <= 1250]. You and the elite guard are both positioned on the integer lattice at different distinct positions (x, y) inside the room such that [0 < x < x_dim, 0 < y < y_dim]. Finally, the maximum distance that the beam can travel before becoming harmless will be given as an integer 1 < distance <= 10000.
> 
> For example, if you and the elite guard were positioned in a room with dimensions [3, 2], your_position [1, 1], guard_position [2, 1], and a maximum shot distance of 4, you could shoot in seven different directions to hit the elite guard (given as vector bearings from your location): [1, 0], [1, 2], [1, -2], [3, 2], [3, -2], [-3, 2], and [-3, -2]. As specific examples, the shot at bearing [1, 0] is the straight line horizontal shot of distance 1, the shot at bearing [-3, -2] bounces off the left wall and then the bottom wall before hitting the elite guard with a total shot distance of sqrt(13), and the shot at bearing [1, 2] bounces off just the top wall before hitting the elite guard with a total shot distance of sqrt(5).

<a href ="#top">(Back to top)</a>
</details> 

## Test Cases

Your code should pass the following test cases.
Note that it may also be run against hidden test cases not shown here.

### Python cases
### Test Case 1

Inputs:

    Solution.solution([3,2], [1,1], [2,1], 4)

Output:

    7
    
### Test Case 2

Inputs:

    Solution.solution([300,275], [150,150], [185,100], 500)
    
Output:

    9

<a align="center" href="#top">(Back to top)</a>

## Languages

- To provide a Java solution, edit Solution.java
- To provide a Python solution, edit solution.py

<a align="center" href="#top">(Back to top)</a>

## Approach 

One example of the problem is visually described below. Suppose the room is 4 x 2, you are standing at (1, 1), and the guard is standing at (2, 1). Then:
   
<img src="https://user-images.githubusercontent.com/81584201/183750405-9c55e917-a553-4f3e-ac96-e028a5e203a7.jpg" width=50% >    

The blue dot represents your position and the red dot represents the guard’s position. The blue ray is a bullet shot that bounces off the top wall and hits the guard. You could also bounce off the bottom wall, or you could fire directly at the guard. The goal is to count how many different shots you could take that will hit the guard (without hitting you).

It’s tempting to want to write a simulation of the bullet ricocheting mechanics and directly count how many shots are valid. But, the key is realizing that a ray that reflects a wall at the same angle is equivalent to a ray that passes right through the wall into a mirror image of the room.

<img src="https://user-images.githubusercontent.com/81584201/183750473-8c6dd75a-6dbf-44ec-94af-aa28e1784c57.jpg" width=50% >    

The benefit of this is that now every bullet has a straight-line path, making it easy to determine its length and whether or not it hits a guard. With this, an outline for the solution is possible.

1. Generate all mirror images of the room in all directions that fit within the maximum range of the bullet.
2. Remove any targets that are farther than the maximum range of the bullet.
3. Compute the angle for every bullet path that hits a target and every bullet path that hits a guard.
4. Count the number of bullet paths that hit a guard without hitting a player.

<a href ="#top">(Back to top)</a>

### Generating mirror images
It’s enough to compute the upper and right mirror images (1st quadrant), since the other images are just reflections of the first quadrant. First, determine the maximum number of images to the right (x_imgs) and above (y_imgs) that can fit within the maximum bullet range (dist). Integer division is used here to prevent splitting a room in half. If even a little a bit of the reflected room is in range, the entire reflection will be included so as to not miss any guards. The guards inside the room outside of the maximal range can be filtered later.
```
    x_imgs = (dist // dimensions[0]) + 2
    y_imgs = (dist // dimensions[1]) + 2
```

Then, create x_imgs × y_imgs mirror images of the room in the first quadrant. Note that if the number of the image is even, it is an exact copy of the original room (it is a reflection of a reflection), but if its odd, the room needs to be reflected. The coordinates of each reflected guard and each reflected player are stored in respective your_pts and guard_pts lists.

```
    ypx, ypy = your_pos[0], your_pos[1]
    gpx, gpy = guard_pos[0], guard_pos[1]
    your_pts = []
    guard_pts = []
    for y_img in range(y_imgs):
        y_off = y_img * dimensions[1]
        your_y = y_off + (ypy if not y_img % 2 else dimensions[1] - ypy) # even images are exact copies, odd needs to be reflected
        guard_y = y_off + (gpy if not y_img % 2 else dimensions[1] - gpy) # even images are exact copies, odd needs to be reflected
        for x_img in range(x_imgs):
            x_off = x_img * dimensions[0]
            your_x = x_off + (ypx if not x_img % 2 else dimensions[0] - ypx) # even images are exact copies, odd needs to be reflected
            guard_x = x_off + (gpx if not x_img % 2 else dimensions[0] - gpx) # even images are exact copies, odd needs to be reflected
            your_pts.append( (your_x, your_y) )
            guard_pts.append( (guard_x, guard_y) )
```

<img src="https://user-images.githubusercontent.com/81584201/183752025-b86dcc9b-aaf5-4a35-afdb-c68a50aa0de7.jpg" width=50% >    

Now, image reflections in quadrants II, III, and IV can be included by reflecting the points in the first quadrant accordingly.

```
def include_reflections(pts):
    '''Returns a list of reflected points in quadrants 1-4 from a list of 1st quadrant points'''
    return [(xr, yr) for x, y in pts for xr, yr in ((x, y), (-x, y), (-x, -y), (x, -y))]

# ...

# include 2nd, 3rd, 4th quadrant pts from reflection of 1st quadrant pts
your_pts = include_reflections(your_pts)
guard_pts = include_reflections(guard_pts)

```

<a href ="#top">(Back to top)</a>

### Removing targets outside of range

Since every bullet has a straight-line path, the length of the path is just the Euclidean distance SQRT((x<sub>1</sub>−x<sub>0</sub>)<sup>2</sup>+(y<sub>1</sub>−y<sub>0</sub>)<sup>2</sup>) between the player (x<sub>0</sub>,y<sub>0</sub>) and the target (x<sub>1</sub>,y<sub>1</sub>). To filter targets outside of range, remove any targets whose path is larger than the maximum range.

```
def distance(x0, y0, x1, y1):
    '''Returns the distance between 2 points (x0, y0) and (x1, y1)'''
    return ((x1-x0)**2 + (y1-y0)**2) ** 0.5

def filter_pts(x, y, pts, dist):
    '''Removes points from pts that are farther than dist away from (x,y)'''
    return [pt for pt in pts if distance(x, y, pt[0], pt[1]) <= dist]

# ...

# remove pts farther than the bullet distance away from the player
    your_pts = filter_pts(ypx, ypy, your_pts, dist)
    guard_pts = filter_pts(ypx, ypy, guard_pts, dist)
```

### Computing bullet angles

There are now two list of coordinates: one representing all the guard reflections in range and a one representing all the player reflections in range. Each shot that hits a guard can then be represented by the angle the path forms with 0 degrees.
<img src="https://user-images.githubusercontent.com/81584201/183751973-de746458-2819-401f-b382-4ddd2aad3663.jpg" width=50%>    

As shown, if the player’s position is (x<sub>0</sub>,y0<sub>0</sub>) and the target is (x<sub>1</sub>,y<sub>1</sub>), then the corresponding angle can be calculated with θ=arctan(<sub>Δx</sub><sup>Δy</sup>). The angle corresponding to each guard and to each player will be saved in a map **guard_angles** and **player_angles** respectively, which map angles to the corresponding target coordinates. If an angle corresponds to multiple guards, only the closest guard will be recorded; likewise for the closest player.

```
def angle(x0, y0, x1, y1):
    # atan2 ensures the quadrant is correct and that theta = (-pi, pi)
    return math.atan2(y1 - y0, x1 - x0) * (180/math.pi)

def get_angles(x0, y0, pts):
    angles = {}
    for x1, y1 in pts:
        ang = angle(x0, y0, x1, y1)
        if ang not in angles:
            # the angle is unique
            angles[ang] = (x1, y1)
        else:
            # the angle is not unique: only record the angle of the closest target along this path
            recorded = angles[ang]
            angles[ang] = min((x1, y1), recorded, key=lambda pt: distance(x0, y0, *pt))
    return angles

# ...

# get map of (angle: pt) where pt is the closest point for a given angle
    your_angles = get_angles(ypx, ypy, your_pts[1:])
    guard_angles = get_angles(ypx, ypy, guard_pts)
```

### Counting valid shots

Finally, only the shots that hit guards but miss players are considered. If the angle for a guard shot does not appear in your_angles, the shot certainly is not aligned with a player reflection. However, if it does appear in your_angles, the shot can only be counted if the guard is closer than the player (otherwise the player will be hit first!).

```
# count how many guards can be shot
    shots = 0
    for ang, pt in guard_angles.iteritems():
        # a guard can only be shot if it's not aligned with a player reflection OR
        # it is closer than an aligned player reflection
        if ang not in your_angles or distance(ypx, ypy, *pt) < distance(ypx, ypy, *your_angles[ang]):
            shots += 1
    return shots
```


### An example test case

For a 3×2 room where the player is positioned at (1,1) and the guard is positioned at (2,1) with a max bullet range of 4:

<img src="https://user-images.githubusercontent.com/81584201/183751893-5c2c467f-52a5-423d-8edb-85241c7bb3f3.jpg" width=50% >    


The black box represents the original room. The blue points are the player and their reflections and the red points are the guard and its reflections. The green paths are all valid bullet paths. Since their are 7 paths, the function returns 7.

<a href ="#top">(Back to top)</a>

## Solution

```
def solution(dimensions, your_pos, guard_pos, dist):
    ypx, ypy = your_pos[0], your_pos[1]
    gpx, gpy = guard_pos[0], guard_pos[1]
    # max number of lattice image copies that fit within dist
    x_imgs = (dist // dimensions[0]) + 2
    y_imgs = (dist // dimensions[1]) + 2
    # find points of the first quadrant
    your_pts = []
    guard_pts = []
    for y_img in range(y_imgs):
        y_off = y_img * dimensions[1]
        your_y = y_off + (ypy if not y_img % 2 else dimensions[1] - ypy)
        guard_y = y_off + (gpy if not y_img % 2 else dimensions[1] - gpy)
        for x_img in range(x_imgs):
            x_off = x_img * dimensions[0]
            your_x = x_off + (ypx if not x_img % 2 else dimensions[0] - ypx)
            guard_x = x_off + (gpx if not x_img % 2 else dimensions[0] - gpx)
            your_pts.append( (your_x, your_y) )
            guard_pts.append( (guard_x, guard_y) )
    # include 2nd, 3rd, 4th quadrant pts from reflection of 1st quadrant pts
    your_pts = include_reflections(your_pts)
    guard_pts = include_reflections(guard_pts)
    # remove pts farther than the bullet distance away from the player
    your_pts = filter_pts(ypx, ypy, your_pts, dist)
    guard_pts = filter_pts(ypx, ypy, guard_pts, dist)
    # get map of (angle: pt) where pt is the closest point for a given angle
    your_angles = get_angles(ypx, ypy, your_pts[1:])
    guard_angles = get_angles(ypx, ypy, guard_pts)
    # count how many guards can be shot
    shots = 0
    for ang, pt in guard_angles.iteritems():
        # a guard can only be shot if it's not aligned with a player reflection OR
        # it is closer than an aligned player reflection
        if ang not in your_angles or distance(ypx, ypy, *pt) < distance(ypx, ypy, *your_angles[ang]):
            shots += 1
    return shots
```

<a href ="#top">(Back to top)</a>
