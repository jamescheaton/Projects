import java.util.Iterator;
import java.util.Collections;
import java.util.*;

public final class RobotController {

    private Tile[][] map;
    private AStarSearch pathFinder;
    private AttackPlayerRobot attackBot;
    private int tileWidth, tileHeight;
    private ArrayList<AStarNode> searchPath = null;
    private boolean pathFound = false;

    private ArrayList<AttackPlayerRobot> attackBots;
    private ArrayList<ArrayList<AStarNode>> pathList;
    private ArrayList<AttackHumanRobot> humanBots; 
    private ArrayList<Human> humanTargets;
    private ConvertHumanRobot convertRobot;
    private UpgradedRobot upgradedRobot;

    private boolean upgraded = false;
    private boolean added = false;

    public RobotController(Tile[][] map, ArrayList<AttackPlayerRobot> attackRobots, ArrayList<AttackHumanRobot> humanBots, ArrayList<Human> humanTargets, ConvertHumanRobot convertBot) {
        int[][] searchMap = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Tile current = map[i][j];
                if (current.getIsMap()) {
                    searchMap[i][j] = 1;
                } else {
                    searchMap[i][j] = 0;
                }
            }
        }
        tileWidth = displayWidth / map[0].length;
        tileHeight = displayHeight / map.length;
        pathFinder = new AStarSearch(searchMap);
        attackBots = attackRobots;
        pathList = new ArrayList<ArrayList<AStarNode>>();
        this.humanBots = humanBots;
        this.map = map;
        this.humanTargets = humanTargets;
        this.convertRobot = convertBot;
    }

    public void renderAll(Player player) {
        BulletController bc = player.getBulletController();
        bc.checkRobotHit(attackBots);
        bc.checkHumanRobotHit(humanBots);
        Iterator<AttackPlayerRobot> iter = attackBots.iterator();
        while (iter.hasNext()) {
            AttackPlayerRobot current = iter.next();
            if (current.getIsDead()) {
                iter.remove();
            }
        }
        Iterator<AttackHumanRobot> hIter = humanBots.iterator();
        while(hIter.hasNext()) {
            AttackHumanRobot current = hIter.next();
            if (current.getIsDead()) {
                hIter.remove();
            }
        }
        for (AttackPlayerRobot current : attackBots) {
            current.pursue(curPlayer);
            //if (current.getFound()) {
              //  break;
            //}
            if (!current.getPathFound()) {
                ArrayList<AStarNode> curPath = findPathToPlayer(player.getRow(), player.getCol(), current);
                pathList.add(curPath);
                current.setPathFound();
                current.setSearchPath(curPath);
            }
            PVector playerPos = player.getPosition().copy();
            PVector botPos = current.getPosition().copy();
            PVector diff = playerPos.sub(botPos);
            float x = diff.x;
            float y = diff.y;
            current.setOrientation(x, y);
            renderRobot(current, player);
        }
        for (AttackHumanRobot current : humanBots) {
            current.render();
            checkOutOfBounds(current);
        }
        if (upgraded) {
            upgradedRobot.render();
        } else {
            convertBot.render();
            checkOutOfBounds(convertBot);
        }
        checkHumanSeen();
        checkHumanHit();
    }
/*
    public void findPathToPlayer(int playerRow, int playerCol) {
        ArrayList<AStarNode> result = pathFinder.search(robot.getRow() , robot.getCol(), playerRow, playerCol);
        if (result != null) {
            searchPath = result;
            attackBot.setPathFound();
            pathFound = true;
        }
    }*/

    public ArrayList<AStarNode> findPathToPlayer(int playerRow, int playerCol, AttackPlayerRobot current) {
        ArrayList<AStarNode> result = pathFinder.search(current.getRow(), current.getCol(), playerRow, playerCol);
        if (result != null) {
            current.setPathFound();
        }
        return result;
    }

    public void renderPath(AttackPlayerRobot attackBot, ArrayList<AStarNode> path) {
        if (attackBot.getPathFound()) {
            fill(0, 0, 255);
            rectMode(CORNER);
            for (AStarNode current : path) {
                rect(current.getCol() * tileWidth, current.getRow() * tileHeight, tileWidth, tileHeight) ;
            }
        }
    }

    public boolean getPathFound() {
        return pathFound;
    }

    /*public void renderRobot() {
        if (pathFound) {
            followPath();
        }
        attackBot.render();
    }*/

    public void renderRobot(AttackPlayerRobot robot, Player player) {
        if (robot.getPathFound()) {
            followPath(robot, player);
        }
        robot.render();
    }

    public void followPath(AttackPlayerRobot attackBot, Player curPlayer) {
        int botRow = attackBot.getRow();
        int botCol = attackBot.getCol();
        PVector botPos = attackBot.getPosition();
        float step = attackBot.getMovementSpeed();
        ArrayList<AStarNode> revPath = new ArrayList<AStarNode>();
        ArrayList<AStarNode> botPath = attackBot.getSearchPath();
        AStarNode goal = botPath.get(0);
        int goalRow = goal.getRow();
        int goalCol = goal.getCol();
        float goalDiff = sqrt(pow(goalRow - botRow, 2) + pow(goalCol - botCol, 2));
        PVector pos = curPlayer.getPosition();
        PVector playerPos = pos.copy();
        PVector curPos = botPos.copy();
        PVector playerDiff = playerPos.sub(curPos);
        float playerDifference = playerDiff.mag();
        if (goalDiff > playerDifference) {
            attackBot.setPathNotFound();
            return;
        }
        for (int i = botPath.size() - 1; i >= 0; i --) {
            revPath.add(botPath.get(i));
        }
        Iterator<AStarNode> iter = revPath.iterator();
        while (iter.hasNext()) {
            AStarNode current = iter.next();
            int curCol = current.getCol();
            int curRow = current.getRow();
            if (botRow == curRow && botCol == curCol) {
                if (iter.hasNext()) {
                    AStarNode next = iter.next();
                    int nextCol = next.getCol();
                    int nextRow = next.getRow();
                    if (nextRow < curRow) {
                        attackBot.moveUp();
                    } else if (nextCol > curCol) {
                        attackBot.moveRight();
                    } else if (nextRow > curRow) {
                        attackBot.moveDown();
                    } else if (nextCol < curCol) {
                        attackBot.moveLeft();
                    }
                    attackBot.setPathFound();
                    return;
                } else {
                    attackBot.setPathNotFound();
                    return;
                }
            }
            
        }

    }

    public void checkOutOfBounds(AttackHumanRobot current) {
        PVector pos = current.getPosition().copy();
        int xPos = (int) pos.x;
        int yPos = (int) pos.y;

        
        int col = (int) pos.x / tileWidth;
        int row = (int) pos.y / tileHeight;

        Tile curTile = map[row][col];
        if (!curTile.getIsMap()) {
            current.reverse();
            current.switchState(HumanState.WANDER);
        }
    }

    public void checkHumanSeen() {
        for (AttackHumanRobot current : humanBots) {
            Iterator<Human> iter = humanTargets.iterator();
            if (!iter.hasNext()) {
                current.switchState(HumanState.WANDER);
            }
            PVector botPos = current.getPosition().copy();
            for (Human target : humanTargets) {
                PVector humanPos = target.getPosition().copy();
                int humanRow = (int) humanPos.y / tileHeight;
                int humanCol = (int) humanPos.x / tileWidth;
                int startRow = (int) botPos.y / tileHeight;
                int startCol = (int) botPos.x / tileWidth;

                PVector bStart = new PVector(startCol, startRow);
                PVector hStart = new PVector(humanCol, humanRow);
                PVector diff = hStart.sub(bStart);

                PVector n = diff.normalize();
                while (startRow != humanRow && startCol != humanCol) {
                    Tile curTile = map[startRow][startCol];
                    if (!curTile.getIsMap()) {
                        current.switchState(HumanState.WANDER);
                        current.removeTarget();                   
                        break;
                    }
                    bStart.add(n);
                    startRow = (int) bStart.y;
                    startCol = (int) bStart.x;
                    current.switchState(HumanState.SEEK);
                    if (!current.isTargetSet()) {
                        current.setTarget(target);
                    }
                }
            }
        }

        
    }

    public void checkHumanHit() {
        for (AttackHumanRobot current : humanBots) {
            PVector curPos = current.getPosition().copy();
            for (Human target : humanTargets) {
                PVector targetPos = target.getPosition().copy();
                PVector diff = targetPos.sub(curPos);
                float dist = diff.mag();
                if (dist < 30) {
                    target.die();
                }
            }
        }
        if (upgraded) {
            return;
        }
        PVector curPos = convertRobot.getPosition().copy();
        for (Human target : humanTargets) {
            PVector targetPos = target.getPosition().copy();
            PVector diff = targetPos.sub(curPos);
            float dist = diff.mag();
            if (dist < 30) {
                target.die();
                upgraded = true;
                int r = (int) targetPos.y / tileHeight;
                int c = (int) targetPos.x / tileWidth;
                upgradedRobot = new UpgradedRobot(c, r, tileWidth, tileHeight, 8f);
            }
        }
    }

    public void checkPlayerHit(Player curPlayer) {
        PVector playerPos = curPlayer.getPosition().copy();
        for (AttackHumanRobot current : humanBots) {
            PVector curPos = current.getPosition().copy();
            float dist = PVector.dist(curPos, playerPos);
            if (dist < 60) {
                current.die();
                curPlayer.die();
            }
        }
        for (AttackPlayerRobot current : attackBots) {
            PVector curPos = current.getPosition().copy();
            float dist = PVector.dist(curPos, playerPos);
            if (dist < 60) {
                current.die();
                curPlayer.die();
            }
        }
    }

    public boolean allEliminated() {
        Iterator<AttackPlayerRobot> playerIter = attackBots.iterator();
        Iterator<AttackHumanRobot> humanIter = humanBots.iterator();
        if (!playerIter.hasNext() && !humanIter.hasNext()) {
            return true;
        } else {
            return false;
        }
    }

    public void freezeAll() {
        for (AttackPlayerRobot current : attackBots) {
            current.freeze();
        }
        for (AttackHumanRobot current : humanBots) {
            current.freeze();
        }
    }


}