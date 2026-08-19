# Akka Payment Orchestrator

A hands-on Scala + Akka project for learning actor-based concurrency, fault tolerance, streams, persistence, and clustering through a payment-processing domain.

## Why this project?

The project models payment/account processing as actors. Commands for one account are handled by one actor mailbox, giving us a concrete way to learn how Akka approaches concurrency without sharing mutable state or manually coordinating locks.

## Stage 1 — Scala + Akka Typed foundation ✅

Current flow:

```text
Main / Guardian
      |
      | spawn
      v
AccountActor(account-123)
      |
      | receives Charge / Refund commands
      v
process one mailbox message at a time
      |
      | replyTo ! Response
      v
Guardian message adapter
```

Concepts covered:

- immutable Scala domain models
- `sealed trait` protocols
- `case class` / `case object`
- pattern matching
- `ActorSystem`
- `Behavior[T]`
- `ActorRef[T]`
- `Behaviors.setup`
- spawning child actors
- fire-and-forget (`!` / tell)
- typed request/reply protocols
- message adapters
- actor mailboxes and sequential processing

### Run locally

Requirements: JDK 17+ and sbt.

```bash
sbt run
```

The sample creates `account-123`, submits a charge and a refund, and logs the actor responses.

## Learning Roadmap

1. **Scala domain model + Akka Typed basics** ✅
2. **Actor communication and state** — next
3. Supervision, retries, and failure handling
4. Akka Streams and backpressure
5. Persistence and event sourcing
6. Cluster Sharding
7. Testing, observability, and Docker

## Interview question unlocked

**Why use an actor instead of synchronizing access to an account object?**

The actor owns its state and processes messages from its mailbox sequentially. Multiple callers can concurrently send commands without directly sharing or mutating the actor's internal state. Different account actors can still execute concurrently, which gives us serialization per entity without globally serializing the system.

> Built incrementally so every stage is runnable and interview-explainable.
