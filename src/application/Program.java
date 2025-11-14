package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();

		printWelcomeScreen();
		sc.nextLine();

		while (!chessMatch.getCheckMate()) {
			try {
				UI.clearScreen();
				printHeader();
				UI.printMach(chessMatch, captured);
				System.out.println();

				System.out.print("🎯 Source: ");
				ChessPosition source = UI.readChessPosition(sc);

				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				printHeader();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				System.out.println();

				System.out.print("🎯 Target: ");
				ChessPosition target = UI.readChessPosition(sc);

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

				if (capturedPiece != null) {
					captured.add(capturedPiece);
					System.out.println("💥 Piece captured: " + capturedPiece);
					sc.nextLine();
				}

				if (chessMatch.getPromoted() != null) {
					System.out.print("👑 Enter piece for promotion (B | N | R | Q): ");
					String type = sc.nextLine().toUpperCase();
					while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
						System.out.println("❌ Invalid value! Enter piece for promotion (B | N | R | Q)");
						type = sc.nextLine().toUpperCase();
					}
					chessMatch.replacePromotedPiece(type);
				}

				if (chessMatch.getCheck()) {
					System.out.println("⚡ CHECK!");
					sc.nextLine();
				}
			}
			catch (ChessException e) {
				System.out.println("❌ " + e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println("❌ " + e.getMessage());
				sc.nextLine();
			}
		}

		// End screen
		UI.clearScreen();
		System.out.println("\n");
		System.out.println("    ╔══════════════════════════════╗");
		System.out.println("    ║          🏁 GAME END         ║");
		System.out.println("    ╠══════════════════════════════╣");
		System.out.println("    ║         🎉 CHECKMATE!        ║");
		System.out.println("    ║                              ║");
		System.out.println("    ║     🏆 WINNER: " + chessMatch.getCurrentPlayer() + " 🏆      ║");
		System.out.println("    ║     📊 Turns: " + chessMatch.getTurn() + "             ║");
		System.out.println("    ║                              ║");
		System.out.println("    ║    Thanks for playing! 👑    ║");
		System.out.println("    ╚══════════════════════════════╝");
		System.out.println("\n");

		UI.printMach(chessMatch, captured);

		sc.close();
	}

	private static void printWelcomeScreen() {
		UI.clearScreen();
		System.out.println("╔══════════════════════════════════════╗");
		System.out.println("║           ♜ CHESS GAME ♜             ║");
		System.out.println("║                                      ║");
		System.out.println("║        Welcome to Chess CLI!         ║");
		System.out.println("║                                      ║");
		System.out.println("║  Commands:                           ║");
		System.out.println("║  • Source: e2 (example)              ║");
		System.out.println("║  • Target: e4 (example)              ║");
		System.out.println("║  • Promotion: Q, R, B, N             ║");
		System.out.println("║                                      ║");
		System.out.println("║        Press Enter to start          ║");
		System.out.println("╚══════════════════════════════════════╝");
	}

	private static void printHeader() {
		System.out.println("♜ CHESS GAME ♜");
		System.out.println("═══════════════");
	}
}