package nl.ou.testar.SimpleGuiStateGraph.strategy.actions;

import nl.ou.testar.SimpleGuiStateGraph.strategy.StrategyGuiState;
import nl.ou.testar.SimpleGuiStateGraph.strategy.StrategyNode;
import nl.ou.testar.SimpleGuiStateGraph.strategy.actionTypes.StrategyNodeActionType;
import org.fruit.alayer.Role;
import org.fruit.alayer.actions.ActionRoles;

import java.util.List;
import java.util.Optional;

public class SnTypeAction extends StrategyNodeActionType {

    public SnTypeAction(final List<StrategyNode> children) {
        super(children);
    }

    @Override
    public Optional<Role> getActionType(final StrategyGuiState state) {
        return Optional.of(ActionRoles.Type);
    }

}
