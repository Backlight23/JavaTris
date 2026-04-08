package com.aquip.tetris.engine.handler;

import com.aquip.tetris.engine.TickContext;
import com.aquip.tetris.engine.PlayerTickContext;
import com.aquip.tetris.state.MatchState;
import com.aquip.tetris.state.PlayerState;
import com.aquip.tetris.placement.PlacementResult;
import com.aquip.tetris.placement.PlaceKey;

import java.util.UUID;

public class GarbageHandler {

    public void apply(MatchState match, TickContext context) {

        for (PlayerState attacker : match.players) {

            validateTarget(attacker, match);

            PlayerTickContext ctx = context.get(attacker);

            if (!ctx.piecePlaced) continue;

            PlacementResult result = getLastPlacement(attacker);
            if (result == null) {
                System.out.println("[GARBAGE] No placement found for " + attacker.player);
                continue;
            }

            PlaceKey key = result.getPlaceKey();

            System.out.println("[GARBAGE] " + attacker.player +
                    " placed: " + key);

            // =====================
            // CALCULATE
            // =====================
            int garbage = attacker.config.garbageTable.get(key);

            System.out.println("[GARBAGE] Base garbage: " + garbage);

            int comboBonus = Math.max(0, attacker.combo.amount() - 1);
            garbage += comboBonus;

            int b2bBonus = (attacker.b2b.amount() > 1) ? 1 : 0;
            garbage += b2bBonus;

            System.out.println("[GARBAGE] After bonuses: " + garbage +
                    " (combo=" + comboBonus + ", b2b=" + b2bBonus + ")");

            if (garbage <= 0) {
                System.out.println("[GARBAGE] No garbage to send.");
                continue;
            }

            // =====================
            // CANCEL
            // =====================
            int beforeCancel = garbage;
            int remaining = cancelIncoming(attacker, garbage);

            System.out.println("[GARBAGE] Cancelled: " +
                    (beforeCancel - remaining) +
                    ", Remaining: " + remaining);

            if (remaining <= 0) continue;

            // =====================
            // TARGET
            // =====================
            PlayerState target = findTarget(match, attacker);

            if (target == null) {
                System.out.println("[GARBAGE] No valid target for " + attacker.player);
                continue;
            }

            System.out.println("[GARBAGE] Sending " + remaining +
                    " lines from " + attacker.player +
                    " → " + target.player);

            // =====================
            // SEND
            // =====================
            target.garbage.incoming.add(remaining);

            System.out.println("[GARBAGE] Target queue now: " +
                    target.garbage.incoming.totalLines());
        }
    }

    private PlacementResult getLastPlacement(PlayerState player) {
        if (player.time.piecesPlaced.isEmpty()) return null;
        return player.time.piecesPlaced.get(player.time.piecesPlaced.size() - 1);
    }

    private int cancelIncoming(PlayerState player, int garbage) {

        if (player.garbage.incoming == null) return garbage;

        int cancelled = player.garbage.incoming.poll(garbage);

        return garbage - cancelled;
    }

    private PlayerState findTarget(MatchState match, PlayerState attacker) {

        if (attacker.garbage.target == null) return null;

        for (PlayerState p : match.players) {
            if (p.player == null) continue;

            if (attacker.garbage.target.equals(p.player.getId())) {
                return p;
            }
        }

        return null;
    }

    private void validateTarget(PlayerState attacker, MatchState match) {

        // If current target is still valid → do nothing
        if (isValidTarget(attacker, match, attacker.garbage.target)) {
            return;
        }

        // Otherwise find valid targets
        var validTargets = match.players.stream()
                .filter(p -> p != attacker)
                .filter(p -> p.status.alive)
                .toList();

        if (validTargets.isEmpty()) {
            // Only log ONCE when transitioning to no targets
            if (attacker.garbage.target != null) {
                System.out.println("[TARGET] Player{" + attacker.player.getId() + "} lost all targets");
            }

            attacker.garbage.target = null;
            return;
        }

        // Pick random new target
        PlayerState newTarget = validTargets.get((int)(Math.random() * validTargets.size()));
        attacker.garbage.target = newTarget.player.getId();

        System.out.println("[TARGET] Player{" + attacker.player.getId() + "} -> New target: " + newTarget.player.getId());
    }

    private boolean isValidTarget(PlayerState attacker, MatchState match, UUID targetId) {
        if (targetId == null) return false;

        for (PlayerState p : match.players) {
            if (p == attacker) continue;
            if (!p.status.alive) continue;

            if (targetId.equals(p.player.getId())) {
                return true;
            }
        }

        return false;
    }
}