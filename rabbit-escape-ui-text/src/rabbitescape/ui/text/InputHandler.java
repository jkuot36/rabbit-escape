package rabbitescape.ui.text;

import java.io.IOException;

import static rabbitescape.engine.i18n.Translation.*;

import rabbitescape.engine.World;
import rabbitescape.engine.err.ExceptionTranslation;
import rabbitescape.engine.err.RabbitEscapeException;
import rabbitescape.engine.solution.*;
import rabbitescape.engine.util.Util;

public class InputHandler
{
    private final SandboxGame sandboxGame;
    private final Terminal terminal;
    private final SolutionRecorder recorder;

    public InputHandler( SandboxGame sandboxGame, Terminal terminal )
    {
        this.sandboxGame = sandboxGame;
        this.terminal = terminal;
        this.recorder = new SolutionRecorder();
    }

    public boolean handle( int commandIndex )
    {
        String input = input();

        input = expandAbbreviations( input );

        if ( input.equals( "help" ) )
        {
            return help();
        }
        else if ( input.equals( "exit" ) )
        {
            sandboxGame.getWorld().changes.explodeAllRabbits();
            sandboxGame.getWorld().step();
            return true;
        }

        try
        {
            Solution partialSolution = SolutionParser.parse( input );

            SolutionRunner.runPartialSolution( partialSolution, sandboxGame );

            if ( partialSolution.commands.length == 0 )
            {
                return fail( t( "Unexpected problem: no SolutionCommand" ) );
            }

            SolutionCommand command =
                partialSolution.commands[ partialSolution.commands.length - 1 ];

            // TODO: until commands step past the last time step, so we
            //       avoid stepping here.
            if (
                ! (
                    (
                           command.actions.length == 1
                        && (
                               command.actions[0] instanceof UntilAction
                            || command.actions[0] instanceof AssertStateAction
                        )
                    )
                )
            )
            {
                // TODO: it's weird we have to do the last time step
                //       outside of runSingleCommand
                try
                {
                    sandboxGame.getWorld().step();
                }
                catch ( World.DontStepAfterFinish e )
                {
                    // Ignore stepping after finish if it's just a wait.
                    if (
                        ! (
                               command.actions.length > 0
                            && command.actions[0] instanceof WaitAction
                        )
                    ) {
                        throw e;
                    }
                }
            }

            appendAll( partialSolution );
        }
        catch ( SolutionExceptions.ProblemRunningSolution e )
        {
            e.commandIndex = commandIndex;
            e.solutionId = 1;
            return fail( ExceptionTranslation.translate( e, terminal.locale ) );
        }
        catch ( RabbitEscapeException e )
        {
            return fail( ExceptionTranslation.translate( e, terminal.locale ) );
        }

        return true;
    }

    /**
     * Note: changes the argument.
     */
    static String expandAbbreviations( String input )
    {
        if ( input.equals( "" ) )
        {
            return "1";
        }
        // Surround coordinates with brackets
        input = Util.regexReplace( input, "\\(?+([0-9]+,[0-9]+)\\)?+", "($1)" );
        // Expand item selection shortcuts
        for ( InputExpansion e : InputExpansion.expansions )
        {
            input = Util.regexReplace(
                input,
                "\\b" + e.character + "\\b",
                e.expansion
            );
        }
        return input;
    }

    private void appendAll( Solution solution )
    {
        recorder.append( solution );
    }

    private boolean help()
    {
        String msg =
            "\n" +
            "Press return to move forward a time step.\n" +
            "Type 'exit' to stop.\n" +
            "Type an ability name (e.g. 'bash') to switch to that ability.\n" +
            "Type '(x,y)' (e.g '(2,3)') to place a item.\n" +
            "Type a number (e.g. '5') to skip that many steps.\n" +
            "\n" +
            "The following abbreviations are available:\n" ;
        for ( InputExpansion e : InputExpansion.expansions )
        {
            msg = msg + e + "\n";
        }
        msg +=
            "Brackets may be omitted when placing items: '2,3'.\n" +
            "Multiple commands may be joined by ';'.\n";

        terminal.out.println( t( msg ) );

        return false;
    }

    private boolean fail( String message )
    {
        terminal.out.println( message );
        return false;
    }

    private String input()
    {
        terminal.out.println(
            t( "Type 'help' then press return for help." ) );

        try
        {
            terminal.out.print( "> " );
            return terminal.in.readLine();
        }
        catch ( IOException e )
        {
            return "";
        }
    }

    public String solution()
    {
        return recorder.getRecord();
    }
}
